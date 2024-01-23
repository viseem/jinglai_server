package cn.iocoder.yudao.module.jl.service.projectcategory;

import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import cn.iocoder.yudao.module.jl.enums.ApprovalTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.auditconfig.AuditConfigRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.service.approval.ApprovalServiceImpl;
import cn.iocoder.yudao.module.jl.utils.NeedAuditHandler;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryApprovalMapper;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryApprovalRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目实验名目的状态变更审批 Service 实现类
 */
@Service
@Validated
public class ProjectCategoryApprovalServiceImpl implements ProjectCategoryApprovalService {
    public static final String PROCESS_KEY = "PROJECT_CATEGORY_STATUS_CHANGE";

    @Resource
    NeedAuditHandler needAuditHandler;

    @Resource
    private BpmProcessInstanceApi processInstanceApi;
    @Resource
    private ApprovalServiceImpl approvalService;
    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectCategoryApprovalRepository projectCategoryApprovalRepository;

    @Resource
    private ProjectCategoryApprovalMapper projectCategoryApprovalMapper;

    @Override
    @Transactional
    public Long createProjectCategoryApproval(ProjectCategoryApprovalCreateReqVO createReqVO) {
        String bpmProcess = BpmProcessInstanceResultEnum.PROCESS.getResult().toString();
        String stage;

        //如果是数据审核 直接改为数据审核状态  审批是审批的数据通不通过
        if (Objects.equals(createReqVO.getStage(), ProjectCategoryStatusEnums.DATA_CHECK.getStatus())) {
            stage = createReqVO.getStage();
        } else {
            stage = null;
        }

        projectCategoryRepository.findById(createReqVO.getProjectCategoryId()).ifPresent(category -> {
            if(stage !=null){
                category.setStage(createReqVO.getStage());
            }
            category.setApprovalStage(bpmProcess);
            projectCategoryRepository.save(category);
        });


        // 插入
        ProjectCategoryApproval projectCategoryApproval = projectCategoryApprovalMapper.toEntity(createReqVO);
        projectCategoryApproval.setApprovalStage(bpmProcess);
        ProjectCategoryApproval save = projectCategoryApprovalRepository.save(projectCategoryApproval);

        if(createReqVO.getNeedAudit()){
            // 发起 BPM 流程
            Map<String, Object> processInstanceVariables = new HashMap<>();
            String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                    new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                            .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(save.getId())));

            // 更新流程实例编号
            projectCategoryApprovalRepository.updateProcessInstanceIdById(processInstanceId, save.getId());
        }else{
            projectCategoryApprovalRepository.updateApprovalStageById(BpmProcessInstanceResultEnum.APPROVE.getResult().toString(),save.getId());
            projectCategoryRepository.updateStageById(createReqVO.getStage(), createReqVO.getProjectCategoryId());
        }


/*        Approval approval = approvalService.processApproval(createReqVO.getUserList(), ApprovalTypeEnums.EXP_PROGRESS.getStatus(), save.getId(),save.getStageMark());
        projectCategoryApprovalRepository.updateApprovalIdById(approval.getId(), save.getId());*/


        // 返回
        return projectCategoryApproval.getId();
    }

    @Override
    public Long saveProjectCategoryApproval(ProjectCategoryApprovalSaveReqVO saveReqVO) {

        ProjectCategoryApproval projectCategoryApprovalCreate = projectCategoryApprovalMapper.toEntity(saveReqVO);
        projectCategoryApprovalRepository.save(projectCategoryApprovalCreate);

        // 插入

        // 返回
        return saveReqVO.getId();
    }

    @Override
    @Transactional
    public void updateProjectCategoryApproval(ProjectCategoryApprovalUpdateReqVO updateReqVO) {


        // 校验存在
        ProjectCategoryApproval projectCategoryApproval = validateProjectCategoryApprovalExists(updateReqVO.getId());

        //任务状态
        String stage = null;

        // 批准该条申请
        if (Objects.equals(updateReqVO.getApprovalStage(), BpmProcessInstanceResultEnum.APPROVE.getResult().toString())) {
            stage = projectCategoryApproval.getStage();
            // 如果是实验审核通过了 就改为已完成
            if(Objects.equals(projectCategoryApproval.getStage(), ProjectCategoryStatusEnums.DATA_CHECK.getStatus())){
                stage = ProjectCategoryStatusEnums.COMPLETE.getStatus();
            }
        }

        // 校验projectCategory是否存在,并修改状态
        String finalStage = stage;
        projectCategoryRepository.findById(projectCategoryApproval.getProjectCategoryId()).ifPresentOrElse(category -> {
            //如果不为空才设置
            if(finalStage!=null){
                category.setStage(finalStage);
            }
            projectCategoryRepository.save(category);
        },()->{
            throw exception(PROJECT_CATEGORY_NOT_EXISTS);
        });

        //更新审批状态 审批人员 审批意见
        projectCategoryApproval.setApprovalStage(updateReqVO.getApprovalStage());
//        projectCategoryApproval.setApprovalUserId(getLoginUserId());
        projectCategoryApprovalRepository.save(projectCategoryApproval);
    }


    @Override
    public void deleteProjectCategoryApproval(Long id) {
        // 校验存在
        validateProjectCategoryApprovalExists(id);
        // 删除
        projectCategoryApprovalRepository.deleteById(id);
    }

    private ProjectCategoryApproval validateProjectCategoryApprovalExists(Long id) {
        Optional<ProjectCategoryApproval> byId = projectCategoryApprovalRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(PROJECT_CATEGORY_APPROVAL_NOT_EXISTS);
        }
        return byId.orElse(null);
    }

    @Override
    public Optional<ProjectCategoryApproval> getProjectCategoryApproval(Long id) {
        return projectCategoryApprovalRepository.findById(id);
    }

    @Override
    public List<ProjectCategoryApproval> getProjectCategoryApprovalList(Collection<Long> ids) {
        return StreamSupport.stream(projectCategoryApprovalRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectCategoryApproval> getProjectCategoryApprovalPage(ProjectCategoryApprovalPageReqVO pageReqVO, ProjectCategoryApprovalPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectCategoryApproval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if (pageReqVO.getStageMark() != null) {
                predicates.add(cb.equal(root.get("stageMark"), pageReqVO.getStageMark()));
            }

            if (pageReqVO.getApprovalUserId() != null) {
                predicates.add(cb.equal(root.get("approvalUserId"), pageReqVO.getApprovalUserId()));
            }

            if (pageReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), pageReqVO.getApprovalMark()));
            }

            if (pageReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), pageReqVO.getApprovalStage()));
            }

            if (pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if (pageReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), pageReqVO.getScheduleId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectCategoryApproval> page = projectCategoryApprovalRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectCategoryApproval> getProjectCategoryApprovalList(ProjectCategoryApprovalExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectCategoryApproval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), exportReqVO.getStage()));
            }

            if (exportReqVO.getStageMark() != null) {
                predicates.add(cb.equal(root.get("stageMark"), exportReqVO.getStageMark()));
            }

            if (exportReqVO.getApprovalUserId() != null) {
                predicates.add(cb.equal(root.get("approvalUserId"), exportReqVO.getApprovalUserId()));
            }

            if (exportReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), exportReqVO.getApprovalMark()));
            }

            if (exportReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), exportReqVO.getApprovalStage()));
            }

            if (exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if (exportReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), exportReqVO.getScheduleId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectCategoryApprovalRepository.findAll(spec);
    }

    private Sort createSort(ProjectCategoryApprovalPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getStage() != null) {
            orders.add(new Sort.Order(order.getStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stage"));
        }

        if (order.getStageMark() != null) {
            orders.add(new Sort.Order(order.getStageMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stageMark"));
        }

        if (order.getApprovalUserId() != null) {
            orders.add(new Sort.Order(order.getApprovalUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalUserId"));
        }

        if (order.getApprovalMark() != null) {
            orders.add(new Sort.Order(order.getApprovalMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalMark"));
        }

        if (order.getApprovalStage() != null) {
            orders.add(new Sort.Order(order.getApprovalStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "approvalStage"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getScheduleId() != null) {
            orders.add(new Sort.Order(order.getScheduleId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "scheduleId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
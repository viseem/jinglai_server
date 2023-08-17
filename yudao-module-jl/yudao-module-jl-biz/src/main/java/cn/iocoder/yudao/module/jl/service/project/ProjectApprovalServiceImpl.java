package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import cn.iocoder.yudao.module.jl.entity.approval.ApprovalProgress;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.repository.approval.ApprovalProgressRepository;
import cn.iocoder.yudao.module.jl.repository.approval.ApprovalRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.approval.ApprovalServiceImpl;
import org.jetbrains.annotations.NotNull;
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

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectApprovalMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectApprovalRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目的状态变更记录 Service 实现类
 *
 */
@Service
@Validated
public class ProjectApprovalServiceImpl implements ProjectApprovalService {

    /**
     * OA 对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "PROJECT_STATUS_CHANGE";
    @Resource
    private BpmProcessInstanceApi processInstanceApi;
    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectApprovalRepository projectApprovalRepository;

    @Resource
    private ProjectApprovalMapper projectApprovalMapper;



    @Override
    @Transactional
    public Long createProjectApproval(ProjectApprovalCreateReqVO createReqVO) {
        // 插入
        ProjectApproval projectApproval = projectApprovalMapper.toEntity(createReqVO);
        projectApproval.setApprovalStage(BpmProcessInstanceResultEnum.PROCESS.getResult().toString());
        ProjectApproval save = projectApprovalRepository.save(projectApproval);

        //同时插入审批表Approval,设置Approval的属性
//        Approval approval = approvalService.processApproval(createReqVO.getUserList(),ApprovalTypeEnums.PROJECT_STATUS_CHANGE.getStatus(), save.getId(),save.getStageMark());
        //修改一下
//        projectApprovalRepository.updateApprovalIdById(approval.getId(),save.getId());

        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(save.getId())));

        // 更新流程实例编号
        projectApprovalRepository.updateProcessInstanceIdById(processInstanceId, save.getId());

        // 返回
        return projectApproval.getId();
    }



    @Override
    public void updateProjectApproval(ProjectApprovalUpdateReqVO updateReqVO) {
        // 校验存在
        ProjectApproval projectApproval = validateProjectApprovalExists(updateReqVO.getId());
        projectApproval.setApprovalStage(updateReqVO.getApprovalStage());


        // 批准该条申请 ： 1. 如果是开展前审批，则变更为开展中
        if (Objects.equals(updateReqVO.getApprovalStage(), BpmProcessInstanceResultEnum.APPROVE.getResult().toString())) {

            // 校验是否存在,并修改状态
            projectRepository.findById(projectApproval.getProjectId()).ifPresentOrElse(project -> {
                if(Objects.equals(projectApproval.getStage(), ProjectStageEnums.DOING_PREVIEW.getStatus())){
                    project.setStage(ProjectStageEnums.DOING.getStatus());
                }else{
                    project.setStage(projectApproval.getStage());
                }
                projectRepository.save(project);
            },()->{
                throw exception(PROJECT_NOT_EXISTS);
            });
        }

        // 更新
//        ProjectApproval updateObj = projectApprovalMapper.toEntity(projectApproval);
        projectApprovalRepository.save(projectApproval);
    }

    @Override
    public void deleteProjectApproval(Long id) {
        // 校验存在
        validateProjectApprovalExists(id);
        // 删除
        projectApprovalRepository.deleteById(id);
    }

    private ProjectApproval validateProjectApprovalExists(Long id) {
        Optional<ProjectApproval> byId = projectApprovalRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(PROJECT_APPROVAL_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<ProjectApproval> getProjectApproval(Long id) {
        return projectApprovalRepository.findById(id);
    }

    @Override
    public List<ProjectApproval> getProjectApprovalList(Collection<Long> ids) {
        return StreamSupport.stream(projectApprovalRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectApproval> getProjectApprovalPage(ProjectApprovalPageReqVO pageReqVO, ProjectApprovalPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectApproval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if(pageReqVO.getStageMark() != null) {
                predicates.add(cb.equal(root.get("stageMark"), pageReqVO.getStageMark()));
            }

            if(pageReqVO.getApprovalUserId() != null) {
                predicates.add(cb.equal(root.get("approvalUserId"), pageReqVO.getApprovalUserId()));
            }

            if(pageReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), pageReqVO.getApprovalMark()));
            }
            if(pageReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), pageReqVO.getApprovalStage()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), pageReqVO.getScheduleId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectApproval> page = projectApprovalRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectApproval> getProjectApprovalList(ProjectApprovalExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectApproval> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), exportReqVO.getStage()));
            }

            if(exportReqVO.getStageMark() != null) {
                predicates.add(cb.equal(root.get("stageMark"), exportReqVO.getStageMark()));
            }

            if(exportReqVO.getApprovalUserId() != null) {
                predicates.add(cb.equal(root.get("approvalUserId"), exportReqVO.getApprovalUserId()));
            }

            if(exportReqVO.getApprovalMark() != null) {
                predicates.add(cb.equal(root.get("approvalMark"), exportReqVO.getApprovalMark()));
            }

            if(exportReqVO.getApprovalStage() != null) {
                predicates.add(cb.equal(root.get("approvalStage"), exportReqVO.getApprovalStage()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getScheduleId() != null) {
                predicates.add(cb.equal(root.get("scheduleId"), exportReqVO.getScheduleId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectApprovalRepository.findAll(spec);
    }

    private Sort createSort(ProjectApprovalPageOrder order) {
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

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getScheduleId() != null) {
            orders.add(new Sort.Order(order.getScheduleId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "scheduleId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
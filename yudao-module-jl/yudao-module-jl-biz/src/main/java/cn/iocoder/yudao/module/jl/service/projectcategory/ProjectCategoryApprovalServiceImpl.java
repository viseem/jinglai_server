package cn.iocoder.yudao.module.jl.service.projectcategory;

import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
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

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProjectCategoryApprovalRepository projectCategoryApprovalRepository;

    @Resource
    private ProjectCategoryApprovalMapper projectCategoryApprovalMapper;

    @Override
    @Transactional
    public Long createProjectCategoryApproval(ProjectCategoryApprovalCreateReqVO createReqVO) {
        //如果是数据审核 直接改为数据审核状态  审批是审批的数据通不通过

        String stage = null;

        if (Objects.equals(createReqVO.getStage(), ProjectCategoryStatusEnums.DATA_CHECK.getStatus())) {
            /*projectCategoryRepository.findById(createReqVO.getProjectCategoryId()).ifPresent(category -> {
                category.setStage(createReqVO.getStage());
                projectCategoryRepository.save(category);
            });*/
            stage = createReqVO.getStage();
        }

        String finalStage = stage;
        projectCategoryRepository.findById(createReqVO.getProjectCategoryId()).ifPresent(category -> {
            if(finalStage !=null){
                category.setStage(createReqVO.getStage());
            }
            category.setApprovalStage("0");
            category.setRequestStage(createReqVO.getStage());
            projectCategoryRepository.save(category);
        });

        // 插入
        ProjectCategoryApproval projectCategoryApproval = projectCategoryApprovalMapper.toEntity(createReqVO);
        projectCategoryApprovalRepository.save(projectCategoryApproval);
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
        if (Objects.equals(updateReqVO.getApprovalStage(), ProjectCategoryStatusEnums.APPROVAL_SUCCESS.getStatus())) {
            stage = updateReqVO.getStage();
            // 如果是实验审核通过了 就改为已完成
            if(Objects.equals(updateReqVO.getStage(), ProjectCategoryStatusEnums.DATA_CHECK.getStatus())){
                stage = ProjectCategoryStatusEnums.COMPLETE.getStatus();
            }
        }

        // 校验projectCategory是否存在,并修改状态、审批状态、审批意见
        String finalStage = stage;
        System.out.println(finalStage);
        projectCategoryRepository.findById(updateReqVO.getProjectCategoryId()).ifPresentOrElse(category -> {
            //如果不为空才设置
            if(finalStage!=null){
                category.setStage(finalStage);
            }
            category.setApprovalStage(updateReqVO.getApprovalStage());
            category.setRequestStage(updateReqVO.getStage());
            projectCategoryRepository.save(category);
        },()->{
            throw exception(PROJECT_CATEGORY_NOT_EXISTS);
        });

        // 更新
//        ProjectCategoryApproval updateObj = projectCategoryApprovalMapper.toEntity(updateReqVO);
        //更新审批状态 审批人员 审批意见
        projectCategoryApproval.setApprovalStage(updateReqVO.getApprovalStage());
        projectCategoryApproval.setApprovalUserId(getLoginUserId());
        projectCategoryApproval.setApprovalMark(updateReqVO.getApprovalMark());
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
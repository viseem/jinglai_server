package cn.iocoder.yudao.module.jl.service.projectcategory;

import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public Long createProjectCategoryApproval(ProjectCategoryApprovalCreateReqVO createReqVO) {
        //如果是数据审核 直接改为数据审核状态  审批是审批的数据通不通过
        if (Objects.equals(createReqVO.getStage(), ProjectCategoryStatusEnums.DATA_CHECK.getStatus())) {
            projectCategoryRepository.findById(createReqVO.getProjectCategoryId()).ifPresent(category -> {
                category.setStage(createReqVO.getStage());
                projectCategoryRepository.save(category);
            });
        }
        // 插入
        ProjectCategoryApproval projectCategoryApproval = projectCategoryApprovalMapper.toEntity(createReqVO);
        projectCategoryApprovalRepository.save(projectCategoryApproval);
        // 返回
        return projectCategoryApproval.getId();
    }

    @Override
    public Long saveProjectCategoryApproval(ProjectCategoryApprovalSaveReqVO saveReqVO) {

        // 批准该条申请
//        if (Objects.equals(saveReqVO.getApprovalStage(), ProjectCategoryStatusEnums.APPROVAL_SUCCESS.getStatus())) {
//
//            // 校验approval是否存在
//            if(saveReqVO.getId()==null){
//                throw exception(PROJECT_CATEGORY_APPROVAL_NOT_EXISTS);
//            }
//            validateProjectCategoryApprovalExists(saveReqVO.getId());
//
//            // 校验projectCategory是否存在,并修改状态
//            projectCategoryRepository.findById(saveReqVO.getProjectCategoryId()).ifPresent(category -> {
//                category.setStage(saveReqVO.getStage());
//                projectCategoryRepository.save(category);
//            });
//
//        }

        // 拒绝该条申请
//        if (Objects.equals(saveReqVO.getApprovalStage(), ProjectCategoryStatusEnums.APPROVAL_FAIL.getStatus())) {
//
//            // 校验approval是否存在
//            if(saveReqVO.getId()==null){
//                throw exception(PROJECT_CATEGORY_APPROVAL_NOT_EXISTS);
//            }
//            validateProjectCategoryApprovalExists(saveReqVO.getId());
//
//        }

        //如果是数据审核 直接改为数据审核状态  审批是审批的数据通不通过
//        if (Objects.equals(saveReqVO.getStage(), ProjectCategoryStatusEnums.DATA_CHECK.getStatus())) {
//            projectCategoryRepository.findById(saveReqVO.getProjectCategoryId()).ifPresent(category -> {
//                category.setStage(saveReqVO.getStage());
//                projectCategoryRepository.save(category);
//            });
//        }

        //只存一条approval，打开这个注释，这个代码是获取主键id，这样save就是修改了；注释掉就只是新增
/*       if(saveReqVO.getProjectCategoryId() != null &&saveReqVO.getProjectCategoryId()>0){
           ProjectCategoryApproval projectCategoryApproval = projectCategoryApprovalRepository.findByProjectCategoryId(saveReqVO.getProjectCategoryId());
           if(projectCategoryApproval !=null && projectCategoryApproval.getId()!=null ){
               saveReqVO.setId(projectCategoryApproval.getId());
           }
       }*/
        ProjectCategoryApproval projectCategoryApprovalCreate = projectCategoryApprovalMapper.toEntity(saveReqVO);
        projectCategoryApprovalRepository.save(projectCategoryApprovalCreate);

        // 插入

        // 返回
        return saveReqVO.getId();
    }

    @Override
    public void updateProjectCategoryApproval(ProjectCategoryApprovalUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectCategoryApprovalExists(updateReqVO.getId());

        // 批准该条申请
        if (Objects.equals(updateReqVO.getApprovalStage(), ProjectCategoryStatusEnums.APPROVAL_SUCCESS.getStatus())) {

            // 校验projectCategory是否存在,并修改状态
            projectCategoryRepository.findById(updateReqVO.getProjectCategoryId()).ifPresent(category -> {
                category.setStage(updateReqVO.getStage());
                projectCategoryRepository.save(category);
            });

        }

        // 更新
        ProjectCategoryApproval updateObj = projectCategoryApprovalMapper.toEntity(updateReqVO);
        projectCategoryApprovalRepository.save(updateObj);
    }


    @Override
    public void deleteProjectCategoryApproval(Long id) {
        // 校验存在
        validateProjectCategoryApprovalExists(id);
        // 删除
        projectCategoryApprovalRepository.deleteById(id);
    }

    private void validateProjectCategoryApprovalExists(Long id) {
        projectCategoryApprovalRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_APPROVAL_NOT_EXISTS));
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
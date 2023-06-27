package cn.iocoder.yudao.module.jl.service.projectcategory;

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
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryAttachmentMapper;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryAttachmentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目实验名目附件 Service 实现类
 *
 */
@Service
@Validated
public class ProjectCategoryAttachmentServiceImpl implements ProjectCategoryAttachmentService {

    @Resource
    private ProjectCategoryAttachmentRepository projectCategoryAttachmentRepository;

    @Resource
    private ProjectCategoryAttachmentMapper projectCategoryAttachmentMapper;

    @Override
    public Long createProjectCategoryAttachment(ProjectCategoryAttachmentCreateReqVO createReqVO) {
        // 插入
        ProjectCategoryAttachment projectCategoryAttachment = projectCategoryAttachmentMapper.toEntity(createReqVO);
        projectCategoryAttachmentRepository.save(projectCategoryAttachment);
        // 返回
        return projectCategoryAttachment.getId();
    }

    @Override
    public void updateProjectCategoryAttachment(ProjectCategoryAttachmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectCategoryAttachmentExists(updateReqVO.getId());
        // 更新
        ProjectCategoryAttachment updateObj = projectCategoryAttachmentMapper.toEntity(updateReqVO);
        projectCategoryAttachmentRepository.save(updateObj);
    }

    @Override
    public void deleteProjectCategoryAttachment(Long id) {
        // 校验存在
        validateProjectCategoryAttachmentExists(id);
        // 删除
        projectCategoryAttachmentRepository.deleteById(id);
    }

    private void validateProjectCategoryAttachmentExists(Long id) {
        projectCategoryAttachmentRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_ATTACHMENT_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectCategoryAttachment> getProjectCategoryAttachment(Long id) {
        return projectCategoryAttachmentRepository.findById(id);
    }

    @Override
    public List<ProjectCategoryAttachment> getProjectCategoryAttachmentList(Collection<Long> ids) {
        return StreamSupport.stream(projectCategoryAttachmentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectCategoryAttachment> getProjectCategoryAttachmentPage(ProjectCategoryAttachmentPageReqVO pageReqVO, ProjectCategoryAttachmentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectCategoryAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getOperatorId() != null) {
                predicates.add(cb.equal(root.get("operatorId"), pageReqVO.getOperatorId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectCategoryAttachment> page = projectCategoryAttachmentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectCategoryAttachment> getProjectCategoryAttachmentList(ProjectCategoryAttachmentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectCategoryAttachment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getOperatorId() != null) {
                predicates.add(cb.equal(root.get("operatorId"), exportReqVO.getOperatorId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectCategoryAttachmentRepository.findAll(spec);
    }

    private Sort createSort(ProjectCategoryAttachmentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getOperatorId() != null) {
            orders.add(new Sort.Order(order.getOperatorId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "operatorId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
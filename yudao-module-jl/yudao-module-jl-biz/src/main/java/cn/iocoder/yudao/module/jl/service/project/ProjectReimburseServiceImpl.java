package cn.iocoder.yudao.module.jl.service.project;

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
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProjectReimburseMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectReimburseRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目报销 Service 实现类
 *
 */
@Service
@Validated
public class ProjectReimburseServiceImpl implements ProjectReimburseService {

    @Resource
    private ProjectReimburseRepository projectReimburseRepository;

    @Resource
    private ProjectReimburseMapper projectReimburseMapper;

    @Override
    public Long createProjectReimburse(ProjectReimburseCreateReqVO createReqVO) {
        // 插入
        ProjectReimburse projectReimburse = projectReimburseMapper.toEntity(createReqVO);
        projectReimburseRepository.save(projectReimburse);
        // 返回
        return projectReimburse.getId();
    }

    @Override
    public void updateProjectReimburse(ProjectReimburseUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectReimburseExists(updateReqVO.getId());
        // 更新
        ProjectReimburse updateObj = projectReimburseMapper.toEntity(updateReqVO);
        projectReimburseRepository.save(updateObj);
    }

    @Override
    public void deleteProjectReimburse(Long id) {
        // 校验存在
        validateProjectReimburseExists(id);
        // 删除
        projectReimburseRepository.deleteById(id);
    }

    private void validateProjectReimburseExists(Long id) {
        projectReimburseRepository.findById(id).orElseThrow(() -> exception(PROJECT_REIMBURSE_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectReimburse> getProjectReimburse(Long id) {
        return projectReimburseRepository.findById(id);
    }

    @Override
    public List<ProjectReimburse> getProjectReimburseList(Collection<Long> ids) {
        return StreamSupport.stream(projectReimburseRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectReimburse> getProjectReimbursePage(ProjectReimbursePageReqVO pageReqVO, ProjectReimbursePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectReimburse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + pageReqVO.getProofName() + "%"));
            }

            if(pageReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), pageReqVO.getProofUrl()));
            }

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectReimburse> page = projectReimburseRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectReimburse> getProjectReimburseList(ProjectReimburseExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectReimburse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + exportReqVO.getProofName() + "%"));
            }

            if(exportReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), exportReqVO.getProofUrl()));
            }

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectReimburseRepository.findAll(spec);
    }

    private Sort createSort(ProjectReimbursePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getProofName() != null) {
            orders.add(new Sort.Order(order.getProofName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofName"));
        }

        if (order.getProofUrl() != null) {
            orders.add(new Sort.Order(order.getProofUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofUrl"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
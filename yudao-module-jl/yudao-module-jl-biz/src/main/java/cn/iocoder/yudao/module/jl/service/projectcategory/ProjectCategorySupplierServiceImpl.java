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
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategorySupplier;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategorySupplierMapper;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategorySupplierRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目实验委外供应商 Service 实现类
 *
 */
@Service
@Validated
public class ProjectCategorySupplierServiceImpl implements ProjectCategorySupplierService {

    @Resource
    private ProjectCategorySupplierRepository projectCategorySupplierRepository;

    @Resource
    private ProjectCategorySupplierMapper projectCategorySupplierMapper;

    @Override
    public Long createProjectCategorySupplier(ProjectCategorySupplierCreateReqVO createReqVO) {
        // 插入
        ProjectCategorySupplier projectCategorySupplier = projectCategorySupplierMapper.toEntity(createReqVO);
        projectCategorySupplierRepository.save(projectCategorySupplier);
        // 返回
        return projectCategorySupplier.getId();
    }

    @Override
    public void updateProjectCategorySupplier(ProjectCategorySupplierUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectCategorySupplierExists(updateReqVO.getId());
        // 更新
        ProjectCategorySupplier updateObj = projectCategorySupplierMapper.toEntity(updateReqVO);
        projectCategorySupplierRepository.save(updateObj);
    }

    @Override
    public void deleteProjectCategorySupplier(Long id) {
        // 校验存在
        validateProjectCategorySupplierExists(id);
        // 删除
        projectCategorySupplierRepository.deleteById(id);
    }

    private void validateProjectCategorySupplierExists(Long id) {
        projectCategorySupplierRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_SUPPLIER_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectCategorySupplier> getProjectCategorySupplier(Long id) {
        return projectCategorySupplierRepository.findById(id);
    }

    @Override
    public List<ProjectCategorySupplier> getProjectCategorySupplierList(Collection<Long> ids) {
        return StreamSupport.stream(projectCategorySupplierRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectCategorySupplier> getProjectCategorySupplierPage(ProjectCategorySupplierPageReqVO pageReqVO, ProjectCategorySupplierPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectCategorySupplier> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getContactName() != null) {
                predicates.add(cb.like(root.get("contactName"), "%" + pageReqVO.getContactName() + "%"));
            }

            if(pageReqVO.getContactPhone() != null) {
                predicates.add(cb.equal(root.get("contactPhone"), pageReqVO.getContactPhone()));
            }

            if(pageReqVO.getAdvantage() != null) {
                predicates.add(cb.equal(root.get("advantage"), pageReqVO.getAdvantage()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectCategorySupplier> page = projectCategorySupplierRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectCategorySupplier> getProjectCategorySupplierList(ProjectCategorySupplierExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectCategorySupplier> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getContactName() != null) {
                predicates.add(cb.like(root.get("contactName"), "%" + exportReqVO.getContactName() + "%"));
            }

            if(exportReqVO.getContactPhone() != null) {
                predicates.add(cb.equal(root.get("contactPhone"), exportReqVO.getContactPhone()));
            }

            if(exportReqVO.getAdvantage() != null) {
                predicates.add(cb.equal(root.get("advantage"), exportReqVO.getAdvantage()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectCategorySupplierRepository.findAll(spec);
    }

    private Sort createSort(ProjectCategorySupplierPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getContactName() != null) {
            orders.add(new Sort.Order(order.getContactName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contactName"));
        }

        if (order.getContactPhone() != null) {
            orders.add(new Sort.Order(order.getContactPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contactPhone"));
        }

        if (order.getAdvantage() != null) {
            orders.add(new Sort.Order(order.getAdvantage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "advantage"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
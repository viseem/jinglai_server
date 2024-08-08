package cn.iocoder.yudao.module.jl.service.projectcategory;

import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import cn.iocoder.yudao.module.jl.enums.FinancePaymentEnums;
import cn.iocoder.yudao.module.jl.repository.financepayment.FinancePaymentRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryOutsourceMapper;
import cn.iocoder.yudao.module.jl.repository.projectcategory.ProjectCategoryOutsourceRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目实验委外 Service 实现类
 *
 */
@Service
@Validated
public class ProjectCategoryOutsourceServiceImpl implements ProjectCategoryOutsourceService {

    @Resource
    private ProjectCategoryOutsourceRepository projectCategoryOutsourceRepository;

    @Resource
    private ProjectCategoryOutsourceMapper projectCategoryOutsourceMapper;

    @Resource
    private FinancePaymentRepository financePaymentRepository;

    @Override
    public Long createProjectCategoryOutsource(ProjectCategoryOutsourceCreateReqVO createReqVO) {
        // 插入
        ProjectCategoryOutsource projectCategoryOutsource = projectCategoryOutsourceMapper.toEntity(createReqVO);
        projectCategoryOutsourceRepository.save(projectCategoryOutsource);
        // 返回
        return projectCategoryOutsource.getId();
    }

    @Override
    public void updateProjectCategoryOutsource(ProjectCategoryOutsourceUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectCategoryOutsourceExists(updateReqVO.getId());
        // 更新
        ProjectCategoryOutsource updateObj = projectCategoryOutsourceMapper.toEntity(updateReqVO);
        projectCategoryOutsourceRepository.save(updateObj);
    }

    @Transactional
    public void updatePaidPrice(Long outsourceId){
        List<FinancePayment> byTypeAndRefIdAndAuditStatus = financePaymentRepository.findByTypeAndRefIdAndAuditStatus("1", outsourceId, FinancePaymentEnums.PAYED.getStatus());
        BigDecimal reduce = byTypeAndRefIdAndAuditStatus.stream().map(FinancePayment::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        projectCategoryOutsourceRepository.updatePaidPriceById(reduce, outsourceId);
    }

    @Override
    public void deleteProjectCategoryOutsource(Long id) {
        // 校验存在
        validateProjectCategoryOutsourceExists(id);
        // 删除
        projectCategoryOutsourceRepository.deleteById(id);
    }

    private void validateProjectCategoryOutsourceExists(Long id) {
        projectCategoryOutsourceRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_OUTSOURCE_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectCategoryOutsource> getProjectCategoryOutsource(Long id) {
        return projectCategoryOutsourceRepository.findById(id);
    }

    @Override
    public List<ProjectCategoryOutsource> getProjectCategoryOutsourceList(Collection<Long> ids) {
        return StreamSupport.stream(projectCategoryOutsourceRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectCategoryOutsource> getProjectCategoryOutsourcePage(ProjectCategoryOutsourcePageReqVO pageReqVO, ProjectCategoryOutsourcePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Specification
        Specification<ProjectCategoryOutsource> spec = getSpecification(pageReqVO);
        List<ProjectCategoryOutsource> content = null;
        long totalElements = 0;
        if(pageReqVO.getPageNo()!=-1){
            // 创建 Pageable 对象
            Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);
            // 执行查询
            Page<ProjectCategoryOutsource> page = projectCategoryOutsourceRepository.findAll(spec, pageable);
             content = page.getContent();
             totalElements = page.getTotalElements();
        }else{
            content = projectCategoryOutsourceRepository.findAll(spec);
        }

        // 转换为 PageResult 并返回
        return new PageResult<>(content, totalElements);
    }

    @NotNull
    private static <T>Specification<T> getSpecification(ProjectCategoryOutsourcePageReqVO pageReqVO) {
        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), pageReqVO.getContent()));
            }

            if(pageReqVO.getCategorySupplierId() != null) {
                predicates.add(cb.equal(root.get("categorySupplierId"), pageReqVO.getCategorySupplierId()));
            }

            if(pageReqVO.getSupplierPrice() != null) {
                predicates.add(cb.equal(root.get("supplierPrice"), pageReqVO.getSupplierPrice()));
            }

            if(pageReqVO.getSalePrice() != null) {
                predicates.add(cb.equal(root.get("salePrice"), pageReqVO.getSalePrice()));
            }

            if(pageReqVO.getBuyPrice() != null) {
                predicates.add(cb.equal(root.get("buyPrice"), pageReqVO.getBuyPrice()));
            }

            if(pageReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + pageReqVO.getProofName() + "%"));
            }

            if(pageReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), pageReqVO.getProofUrl()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return spec;
    }

    private void processItem(ProjectCategoryOutsource item) {
        BigDecimal reduce = item.getPaymentList().stream().filter(payment -> Objects.equals(payment.getAuditStatus(), FinancePaymentEnums.PAYED.getStatus())).map(FinancePayment::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        item.setPaidPrice(reduce);
    }

    @Override
    public List<ProjectCategoryOutsource> getProjectCategoryOutsourceList(ProjectCategoryOutsourceExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectCategoryOutsource> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getContent() != null) {
                predicates.add(cb.equal(root.get("content"), exportReqVO.getContent()));
            }

            if(exportReqVO.getCategorySupplierId() != null) {
                predicates.add(cb.equal(root.get("categorySupplierId"), exportReqVO.getCategorySupplierId()));
            }

            if(exportReqVO.getSupplierPrice() != null) {
                predicates.add(cb.equal(root.get("supplierPrice"), exportReqVO.getSupplierPrice()));
            }

            if(exportReqVO.getSalePrice() != null) {
                predicates.add(cb.equal(root.get("salePrice"), exportReqVO.getSalePrice()));
            }

            if(exportReqVO.getBuyPrice() != null) {
                predicates.add(cb.equal(root.get("buyPrice"), exportReqVO.getBuyPrice()));
            }

            if(exportReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + exportReqVO.getProofName() + "%"));
            }

            if(exportReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), exportReqVO.getProofUrl()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectCategoryOutsourceRepository.findAll(spec);
    }

    private Sort createSort(ProjectCategoryOutsourcePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getContent() != null) {
            orders.add(new Sort.Order(order.getContent().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "content"));
        }

        if (order.getCategorySupplierId() != null) {
            orders.add(new Sort.Order(order.getCategorySupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "categorySupplierId"));
        }

        if (order.getSupplierPrice() != null) {
            orders.add(new Sort.Order(order.getSupplierPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierPrice"));
        }

        if (order.getSalePrice() != null) {
            orders.add(new Sort.Order(order.getSalePrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salePrice"));
        }

        if (order.getBuyPrice() != null) {
            orders.add(new Sort.Order(order.getBuyPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "buyPrice"));
        }

        if (order.getProofName() != null) {
            orders.add(new Sort.Order(order.getProofName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofName"));
        }

        if (order.getProofUrl() != null) {
            orders.add(new Sort.Order(order.getProofUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofUrl"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
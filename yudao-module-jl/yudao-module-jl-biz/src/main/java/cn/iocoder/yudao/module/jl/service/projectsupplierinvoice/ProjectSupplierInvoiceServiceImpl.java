package cn.iocoder.yudao.module.jl.service.projectsupplierinvoice;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
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
import cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice.ProjectSupplierInvoice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.projectsupplierinvoice.ProjectSupplierInvoiceMapper;
import cn.iocoder.yudao.module.jl.repository.projectsupplierinvoice.ProjectSupplierInvoiceRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 采购供应商发票 Service 实现类
 *
 */
@Service
@Validated
public class ProjectSupplierInvoiceServiceImpl implements ProjectSupplierInvoiceService {

    @Resource
    private ProjectSupplierInvoiceRepository projectSupplierInvoiceRepository;

    @Resource
    private ProjectSupplierInvoiceMapper projectSupplierInvoiceMapper;

    //根据supplierId计算总金额
    public Long sumAmountBySupplierId(Long supplierId){
        List<ProjectSupplierInvoice> bySupplierId = projectSupplierInvoiceRepository.findBySupplierId(supplierId);
        Long sum = 0L;
        for (ProjectSupplierInvoice supplierInvoice : bySupplierId) {
            sum += supplierInvoice.getPrice();
        }
        return sum;
    }

    @Override
    public Long createProjectSupplierInvoice(ProjectSupplierInvoiceCreateReqVO createReqVO) {
        // 插入
        ProjectSupplierInvoice projectSupplierInvoice = projectSupplierInvoiceMapper.toEntity(createReqVO);
        projectSupplierInvoiceRepository.save(projectSupplierInvoice);
        // 返回
        return projectSupplierInvoice.getId();
    }

    @Override
    public void updateProjectSupplierInvoice(ProjectSupplierInvoiceUpdateReqVO updateReqVO) {
        // 校验存在
        validateProjectSupplierInvoiceExists(updateReqVO.getId());
        // 更新
        ProjectSupplierInvoice updateObj = projectSupplierInvoiceMapper.toEntity(updateReqVO);
        projectSupplierInvoiceRepository.save(updateObj);
    }

    @Override
    public void deleteProjectSupplierInvoice(Long id) {
        // 校验存在
        validateProjectSupplierInvoiceExists(id);
        // 删除
        projectSupplierInvoiceRepository.deleteById(id);
    }

    private void validateProjectSupplierInvoiceExists(Long id) {
        projectSupplierInvoiceRepository.findById(id).orElseThrow(() -> exception(PROJECT_SUPPLIER_INVOICE_NOT_EXISTS));
    }

    @Override
    public Optional<ProjectSupplierInvoice> getProjectSupplierInvoice(Long id) {
        return projectSupplierInvoiceRepository.findById(id);
    }

    @Override
    public List<ProjectSupplierInvoice> getProjectSupplierInvoiceList(Collection<Long> ids) {
        return StreamSupport.stream(projectSupplierInvoiceRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProjectSupplierInvoice> getProjectSupplierInvoicePage(ProjectSupplierInvoicePageReqVO pageReqVO, ProjectSupplierInvoicePageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProjectSupplierInvoice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if(pageReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + pageReqVO.getFileName() + "%"));
            }

            if(pageReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), pageReqVO.getFileUrl()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if(pageReqVO.getDate() != null) {
                predicates.add(cb.between(root.get("date"), pageReqVO.getDate()[0], pageReqVO.getDate()[1]));
            } 
            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProjectSupplierInvoice> page = projectSupplierInvoiceRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProjectSupplierInvoice> getProjectSupplierInvoiceList(ProjectSupplierInvoiceExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProjectSupplierInvoice> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getFileName() != null) {
                predicates.add(cb.like(root.get("fileName"), "%" + exportReqVO.getFileName() + "%"));
            }

            if(exportReqVO.getFileUrl() != null) {
                predicates.add(cb.equal(root.get("fileUrl"), exportReqVO.getFileUrl()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), exportReqVO.getSupplierId()));
            }

            if(exportReqVO.getDate() != null) {
                predicates.add(cb.between(root.get("date"), exportReqVO.getDate()[0], exportReqVO.getDate()[1]));
            } 
            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return projectSupplierInvoiceRepository.findAll(spec);
    }

    private Sort createSort(ProjectSupplierInvoicePageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getFileName() != null) {
            orders.add(new Sort.Order(order.getFileName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileName"));
        }

        if (order.getFileUrl() != null) {
            orders.add(new Sort.Order(order.getFileUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fileUrl"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getDate() != null) {
            orders.add(new Sort.Order(order.getDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "date"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
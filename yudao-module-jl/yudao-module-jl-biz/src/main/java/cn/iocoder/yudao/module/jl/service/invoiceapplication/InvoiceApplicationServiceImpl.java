package cn.iocoder.yudao.module.jl.service.invoiceapplication;

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
import cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo.*;
import cn.iocoder.yudao.module.jl.entity.invoiceapplication.InvoiceApplication;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.invoiceapplication.InvoiceApplicationMapper;
import cn.iocoder.yudao.module.jl.repository.invoiceapplication.InvoiceApplicationRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 开票申请 Service 实现类
 *
 */
@Service
@Validated
public class InvoiceApplicationServiceImpl implements InvoiceApplicationService {

    @Resource
    private InvoiceApplicationRepository invoiceApplicationRepository;

    @Resource
    private InvoiceApplicationMapper invoiceApplicationMapper;

    @Override
    public Long createInvoiceApplication(InvoiceApplicationCreateReqVO createReqVO) {
        // 插入
        InvoiceApplication invoiceApplication = invoiceApplicationMapper.toEntity(createReqVO);
        invoiceApplicationRepository.save(invoiceApplication);
        // 返回
        return invoiceApplication.getId();
    }

    @Override
    public void updateInvoiceApplication(InvoiceApplicationUpdateReqVO updateReqVO) {
        // 校验存在
        validateInvoiceApplicationExists(updateReqVO.getId());
        // 更新
        InvoiceApplication updateObj = invoiceApplicationMapper.toEntity(updateReqVO);
        invoiceApplicationRepository.save(updateObj);
    }

    @Override
    public void deleteInvoiceApplication(Long id) {
        // 校验存在
        validateInvoiceApplicationExists(id);
        // 删除
        invoiceApplicationRepository.deleteById(id);
    }

    private void validateInvoiceApplicationExists(Long id) {
        invoiceApplicationRepository.findById(id).orElseThrow(() -> exception(INVOICE_APPLICATION_NOT_EXISTS));
    }

    @Override
    public Optional<InvoiceApplication> getInvoiceApplication(Long id) {
        return invoiceApplicationRepository.findById(id);
    }

    @Override
    public List<InvoiceApplication> getInvoiceApplicationList(Collection<Long> ids) {
        return StreamSupport.stream(invoiceApplicationRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InvoiceApplication> getInvoiceApplicationPage(InvoiceApplicationPageReqVO pageReqVO, InvoiceApplicationPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InvoiceApplication> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getCustomerName() != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + pageReqVO.getCustomerName() + "%"));
            }

            if(pageReqVO.getCustomerInvoiceHeadId() != null) {
                predicates.add(cb.equal(root.get("customerInvoiceHeadId"), pageReqVO.getCustomerInvoiceHeadId()));
            }

            if(pageReqVO.getRequire() != null) {
                predicates.add(cb.equal(root.get("require"), pageReqVO.getRequire()));
            }

            if(pageReqVO.getHead() != null) {
                predicates.add(cb.equal(root.get("head"), pageReqVO.getHead()));
            }

            if(pageReqVO.getTaxNumber() != null) {
                predicates.add(cb.equal(root.get("taxNumber"), pageReqVO.getTaxNumber()));
            }

            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if(pageReqVO.getSendAddress() != null) {
                predicates.add(cb.equal(root.get("sendAddress"), pageReqVO.getSendAddress()));
            }

            if(pageReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), pageReqVO.getPhone()));
            }

            if(pageReqVO.getBankName() != null) {
                predicates.add(cb.like(root.get("bankName"), "%" + pageReqVO.getBankName() + "%"));
            }

            if(pageReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), pageReqVO.getBankAccount()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getInvoiceCount() != null) {
                predicates.add(cb.equal(root.get("invoiceCount"), pageReqVO.getInvoiceCount()));
            }

            if(pageReqVO.getTotalAmount() != null) {
                predicates.add(cb.equal(root.get("totalAmount"), pageReqVO.getTotalAmount()));
            }

            if(pageReqVO.getInvoicedAmount() != null) {
                predicates.add(cb.equal(root.get("invoicedAmount"), pageReqVO.getInvoicedAmount()));
            }

            if(pageReqVO.getReceivedAmount() != null) {
                predicates.add(cb.equal(root.get("receivedAmount"), pageReqVO.getReceivedAmount()));
            }

            if(pageReqVO.getRefundedAmount() != null) {
                predicates.add(cb.equal(root.get("refundedAmount"), pageReqVO.getRefundedAmount()));
            }

            if(pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if(pageReqVO.getSalesName() != null) {
                predicates.add(cb.like(root.get("salesName"), "%" + pageReqVO.getSalesName() + "%"));
            }

            if(pageReqVO.getAuditId() != null) {
                predicates.add(cb.equal(root.get("auditId"), pageReqVO.getAuditId()));
            }

            if(pageReqVO.getAuditName() != null) {
                predicates.add(cb.like(root.get("auditName"), "%" + pageReqVO.getAuditName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InvoiceApplication> page = invoiceApplicationRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InvoiceApplication> getInvoiceApplicationList(InvoiceApplicationExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InvoiceApplication> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getCustomerName() != null) {
                predicates.add(cb.like(root.get("customerName"), "%" + exportReqVO.getCustomerName() + "%"));
            }

            if(exportReqVO.getCustomerInvoiceHeadId() != null) {
                predicates.add(cb.equal(root.get("customerInvoiceHeadId"), exportReqVO.getCustomerInvoiceHeadId()));
            }

            if(exportReqVO.getRequire() != null) {
                predicates.add(cb.equal(root.get("require"), exportReqVO.getRequire()));
            }

            if(exportReqVO.getHead() != null) {
                predicates.add(cb.equal(root.get("head"), exportReqVO.getHead()));
            }

            if(exportReqVO.getTaxNumber() != null) {
                predicates.add(cb.equal(root.get("taxNumber"), exportReqVO.getTaxNumber()));
            }

            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if(exportReqVO.getSendAddress() != null) {
                predicates.add(cb.equal(root.get("sendAddress"), exportReqVO.getSendAddress()));
            }

            if(exportReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), exportReqVO.getPhone()));
            }

            if(exportReqVO.getBankName() != null) {
                predicates.add(cb.like(root.get("bankName"), "%" + exportReqVO.getBankName() + "%"));
            }

            if(exportReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), exportReqVO.getBankAccount()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getInvoiceCount() != null) {
                predicates.add(cb.equal(root.get("invoiceCount"), exportReqVO.getInvoiceCount()));
            }

            if(exportReqVO.getTotalAmount() != null) {
                predicates.add(cb.equal(root.get("totalAmount"), exportReqVO.getTotalAmount()));
            }

            if(exportReqVO.getInvoicedAmount() != null) {
                predicates.add(cb.equal(root.get("invoicedAmount"), exportReqVO.getInvoicedAmount()));
            }

            if(exportReqVO.getReceivedAmount() != null) {
                predicates.add(cb.equal(root.get("receivedAmount"), exportReqVO.getReceivedAmount()));
            }

            if(exportReqVO.getRefundedAmount() != null) {
                predicates.add(cb.equal(root.get("refundedAmount"), exportReqVO.getRefundedAmount()));
            }

            if(exportReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), exportReqVO.getSalesId()));
            }

            if(exportReqVO.getSalesName() != null) {
                predicates.add(cb.like(root.get("salesName"), "%" + exportReqVO.getSalesName() + "%"));
            }

            if(exportReqVO.getAuditId() != null) {
                predicates.add(cb.equal(root.get("auditId"), exportReqVO.getAuditId()));
            }

            if(exportReqVO.getAuditName() != null) {
                predicates.add(cb.like(root.get("auditName"), "%" + exportReqVO.getAuditName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return invoiceApplicationRepository.findAll(spec);
    }

    private Sort createSort(InvoiceApplicationPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getCustomerName() != null) {
            orders.add(new Sort.Order(order.getCustomerName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerName"));
        }

        if (order.getCustomerInvoiceHeadId() != null) {
            orders.add(new Sort.Order(order.getCustomerInvoiceHeadId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerInvoiceHeadId"));
        }

        if (order.getRequire() != null) {
            orders.add(new Sort.Order(order.getRequire().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "require"));
        }

        if (order.getHead() != null) {
            orders.add(new Sort.Order(order.getHead().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "head"));
        }

        if (order.getTaxNumber() != null) {
            orders.add(new Sort.Order(order.getTaxNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taxNumber"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getSendAddress() != null) {
            orders.add(new Sort.Order(order.getSendAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendAddress"));
        }

        if (order.getPhone() != null) {
            orders.add(new Sort.Order(order.getPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "phone"));
        }

        if (order.getBankName() != null) {
            orders.add(new Sort.Order(order.getBankName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankName"));
        }

        if (order.getBankAccount() != null) {
            orders.add(new Sort.Order(order.getBankAccount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankAccount"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getInvoiceCount() != null) {
            orders.add(new Sort.Order(order.getInvoiceCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "invoiceCount"));
        }

        if (order.getTotalAmount() != null) {
            orders.add(new Sort.Order(order.getTotalAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "totalAmount"));
        }

        if (order.getInvoicedAmount() != null) {
            orders.add(new Sort.Order(order.getInvoicedAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "invoicedAmount"));
        }

        if (order.getReceivedAmount() != null) {
            orders.add(new Sort.Order(order.getReceivedAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receivedAmount"));
        }

        if (order.getRefundedAmount() != null) {
            orders.add(new Sort.Order(order.getRefundedAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refundedAmount"));
        }

        if (order.getSalesId() != null) {
            orders.add(new Sort.Order(order.getSalesId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesId"));
        }

        if (order.getSalesName() != null) {
            orders.add(new Sort.Order(order.getSalesName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salesName"));
        }

        if (order.getAuditId() != null) {
            orders.add(new Sort.Order(order.getAuditId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditId"));
        }

        if (order.getAuditName() != null) {
            orders.add(new Sort.Order(order.getAuditName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditName"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
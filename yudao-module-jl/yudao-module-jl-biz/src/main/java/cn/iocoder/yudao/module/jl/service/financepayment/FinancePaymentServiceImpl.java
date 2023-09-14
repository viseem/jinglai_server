package cn.iocoder.yudao.module.jl.service.financepayment;

import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.jl.enums.FinancePaymentEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectFeedbackEnums;
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
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo.*;
import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.financepayment.FinancePaymentMapper;
import cn.iocoder.yudao.module.jl.repository.financepayment.FinancePaymentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 财务打款 Service 实现类
 *
 */
@Service
@Validated
public class FinancePaymentServiceImpl implements FinancePaymentService {

    public static final String PROCESS_KEY = "FINANCE_PAYMENT";
    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Resource
    private FinancePaymentRepository financePaymentRepository;

    @Resource
    private FinancePaymentMapper financePaymentMapper;

    @Override
    @Transactional
    public Long createFinancePayment(FinancePaymentCreateReqVO createReqVO) {
        // 插入
        FinancePayment financePayment = financePaymentMapper.toEntity(createReqVO);
        financePaymentRepository.save(financePayment);

        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(financePayment.getId())));


        //更新流程实例id
        financePaymentRepository.updateProcessInstanceIdById(processInstanceId, financePayment.getId());

        // 返回
        return financePayment.getId();
    }

    @Override
    public void updateFinancePayment(FinancePaymentUpdateReqVO updateReqVO) {
        // 校验存在
        validateFinancePaymentExists(updateReqVO.getId());

        if(updateReqVO.getPaymentUrl()!=null){
            updateReqVO.setAuditStatus(FinancePaymentEnums.PAYED.getStatus());
            updateReqVO.setAuditUserId(getLoginUserId());
        }
        // 更新
        FinancePayment updateObj = financePaymentMapper.toEntity(updateReqVO);
        financePaymentRepository.save(updateObj);
    }

    @Override
    public void deleteFinancePayment(Long id) {
        // 校验存在
        validateFinancePaymentExists(id);
        // 删除
        financePaymentRepository.deleteById(id);
    }

    private void validateFinancePaymentExists(Long id) {
        financePaymentRepository.findById(id).orElseThrow(() -> exception(FINANCE_PAYMENT_NOT_EXISTS));
    }

    @Override
    public Optional<FinancePayment> getFinancePayment(Long id) {
        return financePaymentRepository.findById(id);
    }

    @Override
    public List<FinancePayment> getFinancePaymentList(Collection<Long> ids) {
        return StreamSupport.stream(financePaymentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<FinancePayment> getFinancePaymentPage(FinancePaymentPageReqVO pageReqVO, FinancePaymentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<FinancePayment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getAuditUserId() != null) {
                predicates.add(cb.equal(root.get("auditUserId"), pageReqVO.getAuditUserId()));
            }

            if(pageReqVO.getAuditMark() != null) {
                predicates.add(cb.equal(root.get("auditMark"), pageReqVO.getAuditMark()));
            }

            if(pageReqVO.getAuditStatus() != null) {
                predicates.add(cb.equal(root.get("auditStatus"), pageReqVO.getAuditStatus()));
            }

            if(pageReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), pageReqVO.getPrice()));
            }

            if(pageReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), pageReqVO.getProofUrl()));
            }

            if(pageReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + pageReqVO.getProofName() + "%"));
            }

            if(pageReqVO.getPaymentUrl() != null) {
                predicates.add(cb.equal(root.get("paymentUrl"), pageReqVO.getPaymentUrl()));
            }

            if(pageReqVO.getPaymentName() != null) {
                predicates.add(cb.like(root.get("paymentName"), "%" + pageReqVO.getPaymentName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<FinancePayment> page = financePaymentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<FinancePayment> getFinancePaymentList(FinancePaymentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<FinancePayment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getAuditUserId() != null) {
                predicates.add(cb.equal(root.get("auditUserId"), exportReqVO.getAuditUserId()));
            }

            if(exportReqVO.getAuditMark() != null) {
                predicates.add(cb.equal(root.get("auditMark"), exportReqVO.getAuditMark()));
            }

            if(exportReqVO.getAuditStatus() != null) {
                predicates.add(cb.equal(root.get("auditStatus"), exportReqVO.getAuditStatus()));
            }

            if(exportReqVO.getPrice() != null) {
                predicates.add(cb.equal(root.get("price"), exportReqVO.getPrice()));
            }

            if(exportReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), exportReqVO.getProofUrl()));
            }

            if(exportReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + exportReqVO.getProofName() + "%"));
            }

            if(exportReqVO.getPaymentUrl() != null) {
                predicates.add(cb.equal(root.get("paymentUrl"), exportReqVO.getPaymentUrl()));
            }

            if(exportReqVO.getPaymentName() != null) {
                predicates.add(cb.like(root.get("paymentName"), "%" + exportReqVO.getPaymentName() + "%"));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return financePaymentRepository.findAll(spec);
    }

    private Sort createSort(FinancePaymentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getAuditUserId() != null) {
            orders.add(new Sort.Order(order.getAuditUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditUserId"));
        }

        if (order.getAuditMark() != null) {
            orders.add(new Sort.Order(order.getAuditMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditMark"));
        }

        if (order.getAuditStatus() != null) {
            orders.add(new Sort.Order(order.getAuditStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "auditStatus"));
        }

        if (order.getPrice() != null) {
            orders.add(new Sort.Order(order.getPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (order.getProofUrl() != null) {
            orders.add(new Sort.Order(order.getProofUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofUrl"));
        }

        if (order.getProofName() != null) {
            orders.add(new Sort.Order(order.getProofName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofName"));
        }

        if (order.getPaymentUrl() != null) {
            orders.add(new Sort.Order(order.getPaymentUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paymentUrl"));
        }

        if (order.getPaymentName() != null) {
            orders.add(new Sort.Order(order.getPaymentName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paymentName"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
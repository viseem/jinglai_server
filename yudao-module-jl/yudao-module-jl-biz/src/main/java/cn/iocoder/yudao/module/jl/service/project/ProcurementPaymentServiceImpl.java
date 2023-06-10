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
import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProcurementPaymentMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementPaymentRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目采购单打款 Service 实现类
 *
 */
@Service
@Validated
public class ProcurementPaymentServiceImpl implements ProcurementPaymentService {

    @Resource
    private ProcurementPaymentRepository procurementPaymentRepository;

    @Resource
    private ProcurementPaymentMapper procurementPaymentMapper;

    @Override
    public Long createProcurementPayment(ProcurementPaymentCreateReqVO createReqVO) {
        // 插入
        ProcurementPayment procurementPayment = procurementPaymentMapper.toEntity(createReqVO);
        procurementPaymentRepository.save(procurementPayment);
        // 返回
        return procurementPayment.getId();
    }

    @Override
    public void updateProcurementPayment(ProcurementPaymentUpdateReqVO updateReqVO) {
        // 校验存在
        validateProcurementPaymentExists(updateReqVO.getId());
        // 更新
        ProcurementPayment updateObj = procurementPaymentMapper.toEntity(updateReqVO);
        procurementPaymentRepository.save(updateObj);
    }

    @Override
    public void deleteProcurementPayment(Long id) {
        // 校验存在
        validateProcurementPaymentExists(id);
        // 删除
        procurementPaymentRepository.deleteById(id);
    }

    private void validateProcurementPaymentExists(Long id) {
        procurementPaymentRepository.findById(id).orElseThrow(() -> exception(PROCUREMENT_PAYMENT_NOT_EXISTS));
    }

    @Override
    public Optional<ProcurementPayment> getProcurementPayment(Long id) {
        return procurementPaymentRepository.findById(id);
    }

    @Override
    public List<ProcurementPayment> getProcurementPaymentList(Collection<Long> ids) {
        return StreamSupport.stream(procurementPaymentRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProcurementPayment> getProcurementPaymentPage(ProcurementPaymentPageReqVO pageReqVO, ProcurementPaymentPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProcurementPayment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), pageReqVO.getProcurementId()));
            }

            if(pageReqVO.getPaymentDate() != null) {
                predicates.add(cb.between(root.get("paymentDate"), pageReqVO.getPaymentDate()[0], pageReqVO.getPaymentDate()[1]));
            } 
            if(pageReqVO.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), pageReqVO.getAmount()));
            }

            if(pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + pageReqVO.getProofName() + "%"));
            }

            if(pageReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), pageReqVO.getProofUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<ProcurementPayment> page = procurementPaymentRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<ProcurementPayment> getProcurementPaymentList(ProcurementPaymentExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<ProcurementPayment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), exportReqVO.getProcurementId()));
            }

            if(exportReqVO.getPaymentDate() != null) {
                predicates.add(cb.between(root.get("paymentDate"), exportReqVO.getPaymentDate()[0], exportReqVO.getPaymentDate()[1]));
            } 
            if(exportReqVO.getAmount() != null) {
                predicates.add(cb.equal(root.get("amount"), exportReqVO.getAmount()));
            }

            if(exportReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), exportReqVO.getSupplierId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getProofName() != null) {
                predicates.add(cb.like(root.get("proofName"), "%" + exportReqVO.getProofName() + "%"));
            }

            if(exportReqVO.getProofUrl() != null) {
                predicates.add(cb.equal(root.get("proofUrl"), exportReqVO.getProofUrl()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return procurementPaymentRepository.findAll(spec);
    }

    private Sort createSort(ProcurementPaymentPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProcurementId() != null) {
            orders.add(new Sort.Order(order.getProcurementId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "procurementId"));
        }

        if (order.getPaymentDate() != null) {
            orders.add(new Sort.Order(order.getPaymentDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paymentDate"));
        }

        if (order.getAmount() != null) {
            orders.add(new Sort.Order(order.getAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "amount"));
        }

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getProofName() != null) {
            orders.add(new Sort.Order(order.getProofName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofName"));
        }

        if (order.getProofUrl() != null) {
            orders.add(new Sort.Order(order.getProofUrl().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "proofUrl"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
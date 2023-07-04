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
import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.SupplierMapper;
import cn.iocoder.yudao.module.jl.repository.project.SupplierRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目采购单物流信息 Service 实现类
 *
 */
@Service
@Validated
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierRepository supplierRepository;

    @Resource
    private SupplierMapper supplierMapper;

    @Override
    public Long createSupplier(SupplierCreateReqVO createReqVO) {
        // 插入
        Supplier supplier = supplierMapper.toEntity(createReqVO);
        supplierRepository.save(supplier);
        // 返回
        return supplier.getId();
    }

    @Override
    public void updateSupplier(SupplierUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplierExists(updateReqVO.getId());
        // 更新
        Supplier updateObj = supplierMapper.toEntity(updateReqVO);
        supplierRepository.save(updateObj);
    }

    @Override
    public void deleteSupplier(Long id) {
        // 校验存在
        validateSupplierExists(id);
        // 删除
        supplierRepository.deleteById(id);
    }

    private void validateSupplierExists(Long id) {
        supplierRepository.findById(id).orElseThrow(() -> exception(SUPPLIER_NOT_EXISTS));
    }

    @Override
    public Optional<Supplier> getSupplier(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public List<Supplier> getSupplierList(Collection<Long> ids) {
        return StreamSupport.stream(supplierRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Supplier> getSupplierPage(SupplierPageReqVO pageReqVO, SupplierPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Supplier> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getContactName() != null) {
                predicates.add(cb.like(root.get("contactName"), "%" + pageReqVO.getContactName() + "%"));
            }

            if(pageReqVO.getContactPhone() != null) {
                predicates.add(cb.like(root.get("contactPhone"), "%" + pageReqVO.getContactPhone() + "%"));
            }

            if(pageReqVO.getPaymentCycle() != null) {
//                predicates.add(cb.equal(root.get("paymentCycle"), pageReqVO.getPaymentCycle()));
                predicates.add(cb.like(root.get("paymentCycle"), "%" + pageReqVO.getPaymentCycle() + "%"));
            }

            if(pageReqVO.getBankAccount() != null) {
                predicates.add(cb.like(root.get("bankAccount"), "%" + pageReqVO.getBankAccount() + "%"));
            }

            if(pageReqVO.getTaxNumber() != null) {
//                predicates.add(cb.equal(root.get("taxNumber"), pageReqVO.getTaxNumber()));
                predicates.add(cb.like(root.get("taxNumber"), "%" + pageReqVO.getTaxNumber() + "%"));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Supplier> page = supplierRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Supplier> getSupplierList(SupplierExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Supplier> spec = (root, query, cb) -> {
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

            if(exportReqVO.getPaymentCycle() != null) {
                predicates.add(cb.equal(root.get("paymentCycle"), exportReqVO.getPaymentCycle()));
            }

            if(exportReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), exportReqVO.getBankAccount()));
            }

            if(exportReqVO.getTaxNumber() != null) {
                predicates.add(cb.equal(root.get("taxNumber"), exportReqVO.getTaxNumber()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return supplierRepository.findAll(spec);
    }

    private Sort createSort(SupplierPageOrder order) {
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

        if (order.getPaymentCycle() != null) {
            orders.add(new Sort.Order(order.getPaymentCycle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "paymentCycle"));
        }

        if (order.getBankAccount() != null) {
            orders.add(new Sort.Order(order.getBankAccount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankAccount"));
        }

        if (order.getTaxNumber() != null) {
            orders.add(new Sort.Order(order.getTaxNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taxNumber"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
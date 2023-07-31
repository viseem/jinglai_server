package cn.iocoder.yudao.module.jl.service.crm;

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
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceiptHead;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.crm.CrmReceiptHeadMapper;
import cn.iocoder.yudao.module.jl.repository.crm.CrmReceiptHeadRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 客户发票抬头 Service 实现类
 *
 */
@Service
@Validated
public class CrmReceiptHeadServiceImpl implements CrmReceiptHeadService {

    @Resource
    private CrmReceiptHeadRepository crmReceiptHeadRepository;

    @Resource
    private CrmReceiptHeadMapper crmReceiptHeadMapper;

    @Override
    public Long createCrmReceiptHead(CrmReceiptHeadCreateReqVO createReqVO) {
        // 插入
        CrmReceiptHead crmReceiptHead = crmReceiptHeadMapper.toEntity(createReqVO);
        crmReceiptHeadRepository.save(crmReceiptHead);
        // 返回
        return crmReceiptHead.getId();
    }

    @Override
    public void updateCrmReceiptHead(CrmReceiptHeadUpdateReqVO updateReqVO) {
        // 校验存在
        validateCrmReceiptHeadExists(updateReqVO.getId());
        // 更新
        CrmReceiptHead updateObj = crmReceiptHeadMapper.toEntity(updateReqVO);
        crmReceiptHeadRepository.save(updateObj);
    }

    @Override
    public void deleteCrmReceiptHead(Long id) {
        // 校验存在
        validateCrmReceiptHeadExists(id);
        // 删除
        crmReceiptHeadRepository.deleteById(id);
    }

    private void validateCrmReceiptHeadExists(Long id) {
        crmReceiptHeadRepository.findById(id).orElseThrow(() -> exception(CRM_RECEIPT_HEAD_NOT_EXISTS));
    }

    @Override
    public Optional<CrmReceiptHead> getCrmReceiptHead(Long id) {
        return crmReceiptHeadRepository.findById(id);
    }

    @Override
    public List<CrmReceiptHead> getCrmReceiptHeadList(Collection<Long> ids) {
        return StreamSupport.stream(crmReceiptHeadRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CrmReceiptHead> getCrmReceiptHeadPage(CrmReceiptHeadPageReqVO pageReqVO, CrmReceiptHeadPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CrmReceiptHead> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), pageReqVO.getTitle()));
            }

            if(pageReqVO.getTaxerNumber() != null) {
                predicates.add(cb.equal(root.get("taxerNumber"), pageReqVO.getTaxerNumber()));
            }

            if(pageReqVO.getBankName() != null) {
                predicates.add(cb.like(root.get("bankName"), "%" + pageReqVO.getBankName() + "%"));
            }

            if(pageReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), pageReqVO.getBankAccount()));
            }

            if(pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if(pageReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), pageReqVO.getPhone()));
            }

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CrmReceiptHead> page = crmReceiptHeadRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CrmReceiptHead> getCrmReceiptHeadList(CrmReceiptHeadExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CrmReceiptHead> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), exportReqVO.getTitle()));
            }

            if(exportReqVO.getTaxerNumber() != null) {
                predicates.add(cb.equal(root.get("taxerNumber"), exportReqVO.getTaxerNumber()));
            }

            if(exportReqVO.getBankName() != null) {
                predicates.add(cb.like(root.get("bankName"), "%" + exportReqVO.getBankName() + "%"));
            }

            if(exportReqVO.getBankAccount() != null) {
                predicates.add(cb.equal(root.get("bankAccount"), exportReqVO.getBankAccount()));
            }

            if(exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if(exportReqVO.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), exportReqVO.getPhone()));
            }

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return crmReceiptHeadRepository.findAll(spec);
    }

    private Sort createSort(CrmReceiptHeadPageOrder order) {
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

        if (order.getTitle() != null) {
            orders.add(new Sort.Order(order.getTitle().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "title"));
        }

        if (order.getTaxerNumber() != null) {
            orders.add(new Sort.Order(order.getTaxerNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "taxerNumber"));
        }

        if (order.getBankName() != null) {
            orders.add(new Sort.Order(order.getBankName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankName"));
        }

        if (order.getBankAccount() != null) {
            orders.add(new Sort.Order(order.getBankAccount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "bankAccount"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getPhone() != null) {
            orders.add(new Sort.Order(order.getPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "phone"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
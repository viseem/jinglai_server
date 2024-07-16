package cn.iocoder.yudao.module.jl.service.customerchangelog;

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
import cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.customerchangelog.CustomerChangeLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.customerchangelog.CustomerChangeLogMapper;
import cn.iocoder.yudao.module.jl.repository.customerchangelog.CustomerChangeLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 客户变更日志 Service 实现类
 *
 */
@Service
@Validated
public class CustomerChangeLogServiceImpl implements CustomerChangeLogService {

    @Resource
    private CustomerChangeLogRepository customerChangeLogRepository;

    @Resource
    private CustomerChangeLogMapper customerChangeLogMapper;

    @Override
    public Long createCustomerChangeLog(CustomerChangeLogCreateReqVO createReqVO) {
        // 插入
        CustomerChangeLog customerChangeLog = customerChangeLogMapper.toEntity(createReqVO);
        customerChangeLogRepository.save(customerChangeLog);
        // 返回
        return customerChangeLog.getId();
    }

    @Override
    public void updateCustomerChangeLog(CustomerChangeLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateCustomerChangeLogExists(updateReqVO.getId());
        // 更新
        CustomerChangeLog updateObj = customerChangeLogMapper.toEntity(updateReqVO);
        customerChangeLogRepository.save(updateObj);
    }

    @Override
    public void deleteCustomerChangeLog(Long id) {
        // 校验存在
        validateCustomerChangeLogExists(id);
        // 删除
        customerChangeLogRepository.deleteById(id);
    }

    private void validateCustomerChangeLogExists(Long id) {
        customerChangeLogRepository.findById(id).orElseThrow(() -> exception(CUSTOMER_CHANGE_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<CustomerChangeLog> getCustomerChangeLog(Long id) {
        return customerChangeLogRepository.findById(id);
    }

    @Override
    public List<CustomerChangeLog> getCustomerChangeLogList(Collection<Long> ids) {
        return StreamSupport.stream(customerChangeLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<CustomerChangeLog> getCustomerChangeLogPage(CustomerChangeLogPageReqVO pageReqVO, CustomerChangeLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<CustomerChangeLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getToOwnerId() != null) {
                predicates.add(cb.equal(root.get("toOwnerId"), pageReqVO.getToOwnerId()));
            }

            if(pageReqVO.getFromOwnerId() != null) {
                predicates.add(cb.equal(root.get("fromOwnerId"), pageReqVO.getFromOwnerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<CustomerChangeLog> page = customerChangeLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<CustomerChangeLog> getCustomerChangeLogList(CustomerChangeLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<CustomerChangeLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), exportReqVO.getCustomerId()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getToOwnerId() != null) {
                predicates.add(cb.equal(root.get("toOwnerId"), exportReqVO.getToOwnerId()));
            }

            if(exportReqVO.getFromOwnerId() != null) {
                predicates.add(cb.equal(root.get("fromOwnerId"), exportReqVO.getFromOwnerId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return customerChangeLogRepository.findAll(spec);
    }

    private Sort createSort(CustomerChangeLogPageOrder order) {
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

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getToOwnerId() != null) {
            orders.add(new Sort.Order(order.getToOwnerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "toOwnerId"));
        }

        if (order.getFromOwnerId() != null) {
            orders.add(new Sort.Order(order.getFromOwnerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "fromOwnerId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
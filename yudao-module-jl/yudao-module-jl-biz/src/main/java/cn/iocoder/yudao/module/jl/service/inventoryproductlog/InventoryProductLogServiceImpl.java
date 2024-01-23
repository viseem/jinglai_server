package cn.iocoder.yudao.module.jl.service.inventoryproductlog;

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
import cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventoryproductlog.InventoryProductLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventoryproductlog.InventoryProductLogMapper;
import cn.iocoder.yudao.module.jl.repository.inventoryproductlog.InventoryProductLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 产品变更日志 Service 实现类
 *
 */
@Service
@Validated
public class InventoryProductLogServiceImpl implements InventoryProductLogService {

    @Resource
    private InventoryProductLogRepository inventoryProductLogRepository;

    @Resource
    private InventoryProductLogMapper inventoryProductLogMapper;

    @Override
    public Long createInventoryProductLog(InventoryProductLogCreateReqVO createReqVO) {
        // 插入
        InventoryProductLog inventoryProductLog = inventoryProductLogMapper.toEntity(createReqVO);
        inventoryProductLogRepository.save(inventoryProductLog);
        // 返回
        return inventoryProductLog.getId();
    }

    @Override
    public void updateInventoryProductLog(InventoryProductLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateInventoryProductLogExists(updateReqVO.getId());
        // 更新
        InventoryProductLog updateObj = inventoryProductLogMapper.toEntity(updateReqVO);
        inventoryProductLogRepository.save(updateObj);
    }

    @Override
    public void deleteInventoryProductLog(Long id) {
        // 校验存在
        validateInventoryProductLogExists(id);
        // 删除
        inventoryProductLogRepository.deleteById(id);
    }

    private void validateInventoryProductLogExists(Long id) {
        inventoryProductLogRepository.findById(id).orElseThrow(() -> exception(INVENTORY_PRODUCT_LOG_NOT_EXISTS));
    }

    @Override
    public Optional<InventoryProductLog> getInventoryProductLog(Long id) {
        return inventoryProductLogRepository.findById(id);
    }

    @Override
    public List<InventoryProductLog> getInventoryProductLogList(Collection<Long> ids) {
        return StreamSupport.stream(inventoryProductLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InventoryProductLog> getInventoryProductLogPage(InventoryProductLogPageReqVO pageReqVO, InventoryProductLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InventoryProductLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
            }

            if(pageReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), pageReqVO.getLocation()));
            }

            if(pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getExperId() != null) {
                predicates.add(cb.equal(root.get("experId"), pageReqVO.getExperId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InventoryProductLog> page = inventoryProductLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InventoryProductLog> getInventoryProductLogList(InventoryProductLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InventoryProductLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), exportReqVO.getProjectSupplyId()));
            }

            if(exportReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), exportReqVO.getLocation()));
            }

            if(exportReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), exportReqVO.getQuantity()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getExperId() != null) {
                predicates.add(cb.equal(root.get("experId"), exportReqVO.getExperId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return inventoryProductLogRepository.findAll(spec);
    }

    private Sort createSort(InventoryProductLogPageOrder order) {
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

        if (order.getProjectSupplyId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyId"));
        }

        if (order.getLocation() != null) {
            orders.add(new Sort.Order(order.getLocation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "location"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getExperId() != null) {
            orders.add(new Sort.Order(order.getExperId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "experId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
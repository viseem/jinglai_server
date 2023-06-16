package cn.iocoder.yudao.module.jl.service.inventory;

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
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryCheckInMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryCheckInRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 签收记录 Service 实现类
 *
 */
@Service
@Validated
public class InventoryCheckInServiceImpl implements InventoryCheckInService {

    @Resource
    private InventoryCheckInRepository inventoryCheckInRepository;

    @Resource
    private InventoryCheckInMapper inventoryCheckInMapper;

    @Override
    public Long createInventoryCheckIn(InventoryCheckInCreateReqVO createReqVO) {
        // 插入
        InventoryCheckIn inventoryCheckIn = inventoryCheckInMapper.toEntity(createReqVO);
        inventoryCheckInRepository.save(inventoryCheckIn);
        // 返回
        return inventoryCheckIn.getId();
    }

    @Override
    public void updateInventoryCheckIn(InventoryCheckInUpdateReqVO updateReqVO) {
        // 校验存在
        validateInventoryCheckInExists(updateReqVO.getId());
        // 更新
        InventoryCheckIn updateObj = inventoryCheckInMapper.toEntity(updateReqVO);
        inventoryCheckInRepository.save(updateObj);
    }

    @Override
    public void deleteInventoryCheckIn(Long id) {
        // 校验存在
        validateInventoryCheckInExists(id);
        // 删除
        inventoryCheckInRepository.deleteById(id);
    }

    private void validateInventoryCheckInExists(Long id) {
        inventoryCheckInRepository.findById(id).orElseThrow(() -> exception(INVENTORY_CHECK_IN_NOT_EXISTS));
    }

    @Override
    public Optional<InventoryCheckIn> getInventoryCheckIn(Long id) {
        return inventoryCheckInRepository.findById(id);
    }

    @Override
    public List<InventoryCheckIn> getInventoryCheckInList(Collection<Long> ids) {
        return StreamSupport.stream(inventoryCheckInRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InventoryCheckIn> getInventoryCheckInPage(InventoryCheckInPageReqVO pageReqVO, InventoryCheckInPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InventoryCheckIn> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
            }

            if(pageReqVO.getInQuantity() != null) {
                predicates.add(cb.equal(root.get("inQuantity"), pageReqVO.getInQuantity()));
            }

            if(pageReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), pageReqVO.getType()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if(pageReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), pageReqVO.getRefId()));
            }

            if(pageReqVO.getRefItemId() != null) {
                predicates.add(cb.equal(root.get("refItemId"), pageReqVO.getRefItemId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InventoryCheckIn> page = inventoryCheckInRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InventoryCheckIn> getInventoryCheckInList(InventoryCheckInExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InventoryCheckIn> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), exportReqVO.getProjectSupplyId()));
            }

            if(exportReqVO.getInQuantity() != null) {
                predicates.add(cb.equal(root.get("inQuantity"), exportReqVO.getInQuantity()));
            }

            if(exportReqVO.getType() != null) {
                predicates.add(cb.equal(root.get("type"), exportReqVO.getType()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }

            if(exportReqVO.getRefId() != null) {
                predicates.add(cb.equal(root.get("refId"), exportReqVO.getRefId()));
            }

            if(exportReqVO.getRefItemId() != null) {
                predicates.add(cb.equal(root.get("refItemId"), exportReqVO.getRefItemId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return inventoryCheckInRepository.findAll(spec);
    }

    private Sort createSort(InventoryCheckInPageOrder order) {
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

        if (order.getProjectSupplyId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyId"));
        }

        if (order.getInQuantity() != null) {
            orders.add(new Sort.Order(order.getInQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "inQuantity"));
        }

        if (order.getType() != null) {
            orders.add(new Sort.Order(order.getType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "type"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getRefId() != null) {
            orders.add(new Sort.Order(order.getRefId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refId"));
        }

        if (order.getRefItemId() != null) {
            orders.add(new Sort.Order(order.getRefItemId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "refItemId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
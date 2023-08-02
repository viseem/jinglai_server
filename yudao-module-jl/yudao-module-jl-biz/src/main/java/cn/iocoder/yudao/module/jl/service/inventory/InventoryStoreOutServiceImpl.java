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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryStoreOutMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryStoreOutRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 出库记录 Service 实现类
 *
 */
@Service
@Validated
public class InventoryStoreOutServiceImpl implements InventoryStoreOutService {

    @Resource
    private InventoryStoreOutRepository inventoryStoreOutRepository;

    @Resource
    private InventoryStoreOutMapper inventoryStoreOutMapper;

    @Override
    public Long createInventoryStoreOut(InventoryStoreOutCreateReqVO createReqVO) {
        // 插入
        InventoryStoreOut inventoryStoreOut = inventoryStoreOutMapper.toEntity(createReqVO);
        inventoryStoreOutRepository.save(inventoryStoreOut);
        // 返回
        return inventoryStoreOut.getId();
    }

    @Override
    public void updateInventoryStoreOut(InventoryStoreOutUpdateReqVO updateReqVO) {
        // 校验存在
        validateInventoryStoreOutExists(updateReqVO.getId());
        // 更新
        InventoryStoreOut updateObj = inventoryStoreOutMapper.toEntity(updateReqVO);
        inventoryStoreOutRepository.save(updateObj);
    }

    @Override
    public void deleteInventoryStoreOut(Long id) {
        // 校验存在
        validateInventoryStoreOutExists(id);
        // 删除
        inventoryStoreOutRepository.deleteById(id);
    }

    private void validateInventoryStoreOutExists(Long id) {
        inventoryStoreOutRepository.findById(id).orElseThrow(() -> exception(INVENTORY_STORE_OUT_NOT_EXISTS));
    }

    @Override
    public Optional<InventoryStoreOut> getInventoryStoreOut(Long id) {
        return inventoryStoreOutRepository.findById(id);
    }

    @Override
    public List<InventoryStoreOut> getInventoryStoreOutList(Collection<Long> ids) {
        return StreamSupport.stream(inventoryStoreOutRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InventoryStoreOut> getInventoryStoreOutPage(InventoryStoreOutPageReqVO pageReqVO, InventoryStoreOutPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InventoryStoreOut> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
            }

            if(pageReqVO.getOutQuantity() != null) {
                predicates.add(cb.equal(root.get("outQuantity"), pageReqVO.getOutQuantity()));
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

            if(pageReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), pageReqVO.getTemperature()));
            }

            if(pageReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), pageReqVO.getValidDate()[0], pageReqVO.getValidDate()[1]));
            } 
            if(pageReqVO.getApplyUser() != null) {
                predicates.add(cb.equal(root.get("applyUser"), pageReqVO.getApplyUser()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InventoryStoreOut> page = inventoryStoreOutRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InventoryStoreOut> getInventoryStoreOutList(InventoryStoreOutExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InventoryStoreOut> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), exportReqVO.getProjectSupplyId()));
            }

            if(exportReqVO.getOutQuantity() != null) {
                predicates.add(cb.equal(root.get("outQuantity"), exportReqVO.getOutQuantity()));
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

            if(exportReqVO.getTemperature() != null) {
                predicates.add(cb.equal(root.get("temperature"), exportReqVO.getTemperature()));
            }

            if(exportReqVO.getValidDate() != null) {
                predicates.add(cb.between(root.get("validDate"), exportReqVO.getValidDate()[0], exportReqVO.getValidDate()[1]));
            } 
            if(exportReqVO.getApplyUser() != null) {
                predicates.add(cb.equal(root.get("applyUser"), exportReqVO.getApplyUser()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return inventoryStoreOutRepository.findAll(spec);
    }

    private Sort createSort(InventoryStoreOutPageOrder order) {
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

        if (order.getOutQuantity() != null) {
            orders.add(new Sort.Order(order.getOutQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "outQuantity"));
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

        if (order.getTemperature() != null) {
            orders.add(new Sort.Order(order.getTemperature().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "temperature"));
        }

        if (order.getValidDate() != null) {
            orders.add(new Sort.Order(order.getValidDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "validDate"));
        }

        if (order.getApplyUser() != null) {
            orders.add(new Sort.Order(order.getApplyUser().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "applyUser"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
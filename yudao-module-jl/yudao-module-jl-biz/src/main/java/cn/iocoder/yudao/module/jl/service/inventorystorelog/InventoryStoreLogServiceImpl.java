package cn.iocoder.yudao.module.jl.service.inventorystorelog;

import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;
import cn.iocoder.yudao.module.jl.service.project.ProcurementItemServiceImpl;
import liquibase.pro.packaged.R;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
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
import cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventorystorelog.InventoryStoreLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.inventorystorelog.InventoryStoreLogMapper;
import cn.iocoder.yudao.module.jl.repository.inventorystorelog.InventoryStoreLogRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 物品出入库日志 Service 实现类
 *
 */
@Service
@Validated
public class InventoryStoreLogServiceImpl implements InventoryStoreLogService {

    @Resource
    private InventoryStoreLogRepository inventoryStoreLogRepository;

    @Resource
    private InventoryStoreLogMapper inventoryStoreLogMapper;

    @Resource
    private ProcurementItemOnlyRepository procurementItemOnlyRepository;

    @Resource
    private ProcurementItemServiceImpl procurementItemService;

    @Override
    @Transactional
    public Long createInventoryStoreLog(InventoryStoreLogCreateReqVO createReqVO) {

        Optional<ProcurementItem> byId = procurementItemOnlyRepository.findById(createReqVO.getSourceItemId());
        if (byId.isEmpty()) {
            throw exception(PROCUREMENT_ITEM_NOT_EXISTS);
        }
        ProcurementItem procurementItem = byId.get();
        createReqVO.setName(procurementItem.getName());
        createReqVO.setSource(procurementItem.getSource());
        createReqVO.setBrand(procurementItem.getBrand());
        createReqVO.setSpec(procurementItem.getSpec());
        createReqVO.setUnit(procurementItem.getUnit());
        createReqVO.setCatalogNumber(procurementItem.getCatalogNumber());
        // 插入
        InventoryStoreLog inventoryStoreLog = inventoryStoreLogMapper.toEntity(createReqVO);
        inventoryStoreLogRepository.save(inventoryStoreLog);


        procurementItemService.updateStockQuantity(procurementItem.getId());

        // 返回
        return inventoryStoreLog.getId();
    }

    @Override
    @Transactional
    public void updateInventoryStoreLog(InventoryStoreLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateInventoryStoreLogExists(updateReqVO.getId());
        // 更新
        InventoryStoreLog updateObj = inventoryStoreLogMapper.toEntity(updateReqVO);
        inventoryStoreLogRepository.save(updateObj);

        procurementItemService.updateStockQuantity(updateObj.getSourceItemId());
    }

    @Override
    @Transactional
    public void deleteInventoryStoreLog(Long id) {
        // 校验存在
        InventoryStoreLog inventoryStoreLog = validateInventoryStoreLogExists(id);
        // 删除
        inventoryStoreLogRepository.deleteById(id);
        System.out.println("-=-=-="+inventoryStoreLog.getSourceItemId());
        procurementItemService.updateStockQuantity(inventoryStoreLog.getSourceItemId());
    }

    private InventoryStoreLog validateInventoryStoreLogExists(Long id) {
        Optional<InventoryStoreLog> byId = inventoryStoreLogRepository.findById(id);
        if (byId.isEmpty()) {
            throw exception(INVENTORY_STORE_LOG_NOT_EXISTS);
        }
        return byId.get();
    }

    @Override
    public Optional<InventoryStoreLog> getInventoryStoreLog(Long id) {
        return inventoryStoreLogRepository.findById(id);
    }

    @Override
    public List<InventoryStoreLog> getInventoryStoreLogList(Collection<Long> ids) {
        return StreamSupport.stream(inventoryStoreLogRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<InventoryStoreLog> getInventoryStoreLogPage(InventoryStoreLogPageReqVO pageReqVO, InventoryStoreLogPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<InventoryStoreLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getIsIn() != null) {
                if(pageReqVO.getIsIn()){
                    // 查询changeNum大于0的
                    predicates.add(cb.greaterThan(root.get("changeNum"), BigDecimal.ZERO));
                }else{
                    // 查询changeNum小于0的
                    predicates.add(cb.lessThan(root.get("changeNum"), BigDecimal.ZERO));
                }
            }


            if(pageReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), pageReqVO.getSource()));
            }

            if(pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if(pageReqVO.getCatalogNumber() != null) {
                predicates.add(cb.equal(root.get("catalogNumber"), pageReqVO.getCatalogNumber()));
            }

            if(pageReqVO.getBrand() != null) {
                predicates.add(cb.equal(root.get("brand"), pageReqVO.getBrand()));
            }

            if(pageReqVO.getSpec() != null) {
                predicates.add(cb.equal(root.get("spec"), pageReqVO.getSpec()));
            }

            if(pageReqVO.getUnit() != null) {
                predicates.add(cb.equal(root.get("unit"), pageReqVO.getUnit()));
            }

            if(pageReqVO.getChangeNum() != null) {
                predicates.add(cb.equal(root.get("changeNum"), pageReqVO.getChangeNum()));
            }

            if(pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if(pageReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), pageReqVO.getRoomId()));
            }

            if(pageReqVO.getContainerId() != null) {
                predicates.add(cb.equal(root.get("containerId"), pageReqVO.getContainerId()));
            }

            if(pageReqVO.getPlaceId() != null) {
                predicates.add(cb.equal(root.get("placeId"), pageReqVO.getPlaceId()));
            }

            if(pageReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), pageReqVO.getLocation()));
            }

            if(pageReqVO.getSourceItemId() != null) {
                predicates.add(cb.equal(root.get("sourceItemId"), pageReqVO.getSourceItemId()));
            }

            if(pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
            }

            if(pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if(pageReqVO.getPurchaseContractId() != null) {
                predicates.add(cb.equal(root.get("purchaseContractId"), pageReqVO.getPurchaseContractId()));
            }

            if(pageReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), pageReqVO.getProcurementId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<InventoryStoreLog> page = inventoryStoreLogRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<InventoryStoreLog> getInventoryStoreLogList(InventoryStoreLogExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<InventoryStoreLog> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(exportReqVO.getSource() != null) {
                predicates.add(cb.equal(root.get("source"), exportReqVO.getSource()));
            }

            if(exportReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + exportReqVO.getName() + "%"));
            }

            if(exportReqVO.getCatalogNumber() != null) {
                predicates.add(cb.equal(root.get("catalogNumber"), exportReqVO.getCatalogNumber()));
            }

            if(exportReqVO.getBrand() != null) {
                predicates.add(cb.equal(root.get("brand"), exportReqVO.getBrand()));
            }

            if(exportReqVO.getSpec() != null) {
                predicates.add(cb.equal(root.get("spec"), exportReqVO.getSpec()));
            }

            if(exportReqVO.getUnit() != null) {
                predicates.add(cb.equal(root.get("unit"), exportReqVO.getUnit()));
            }

            if(exportReqVO.getChangeNum() != null) {
                predicates.add(cb.equal(root.get("changeNum"), exportReqVO.getChangeNum()));
            }

            if(exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if(exportReqVO.getRoomId() != null) {
                predicates.add(cb.equal(root.get("roomId"), exportReqVO.getRoomId()));
            }

            if(exportReqVO.getContainerId() != null) {
                predicates.add(cb.equal(root.get("containerId"), exportReqVO.getContainerId()));
            }

            if(exportReqVO.getPlaceId() != null) {
                predicates.add(cb.equal(root.get("placeId"), exportReqVO.getPlaceId()));
            }

            if(exportReqVO.getLocation() != null) {
                predicates.add(cb.equal(root.get("location"), exportReqVO.getLocation()));
            }

            if(exportReqVO.getSourceItemId() != null) {
                predicates.add(cb.equal(root.get("sourceItemId"), exportReqVO.getSourceItemId()));
            }

            if(exportReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), exportReqVO.getProjectSupplyId()));
            }

            if(exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if(exportReqVO.getPurchaseContractId() != null) {
                predicates.add(cb.equal(root.get("purchaseContractId"), exportReqVO.getPurchaseContractId()));
            }

            if(exportReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), exportReqVO.getProcurementId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return inventoryStoreLogRepository.findAll(spec);
    }

    private Sort createSort(InventoryStoreLogPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getSource() != null) {
            orders.add(new Sort.Order(order.getSource().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "source"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getCatalogNumber() != null) {
            orders.add(new Sort.Order(order.getCatalogNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "catalogNumber"));
        }

        if (order.getBrand() != null) {
            orders.add(new Sort.Order(order.getBrand().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "brand"));
        }

        if (order.getSpec() != null) {
            orders.add(new Sort.Order(order.getSpec().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "spec"));
        }

        if (order.getUnit() != null) {
            orders.add(new Sort.Order(order.getUnit().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "unit"));
        }

        if (order.getChangeNum() != null) {
            orders.add(new Sort.Order(order.getChangeNum().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "changeNum"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getRoomId() != null) {
            orders.add(new Sort.Order(order.getRoomId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "roomId"));
        }

        if (order.getContainerId() != null) {
            orders.add(new Sort.Order(order.getContainerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "containerId"));
        }

        if (order.getPlaceId() != null) {
            orders.add(new Sort.Order(order.getPlaceId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "placeId"));
        }

        if (order.getLocation() != null) {
            orders.add(new Sort.Order(order.getLocation().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "location"));
        }

        if (order.getSourceItemId() != null) {
            orders.add(new Sort.Order(order.getSourceItemId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sourceItemId"));
        }

        if (order.getProjectSupplyId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyId"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getPurchaseContractId() != null) {
            orders.add(new Sort.Order(order.getPurchaseContractId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "purchaseContractId"));
        }

        if (order.getProcurementId() != null) {
            orders.add(new Sort.Order(order.getProcurementId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "procurementId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
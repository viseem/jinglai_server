package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryRoom;
import cn.iocoder.yudao.module.jl.entity.inventorystorelog.InventoryStoreLog;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItemOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.enums.ProcurementItemStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryRoomRepository;
import cn.iocoder.yudao.module.jl.repository.inventorystorelog.InventoryStoreLogRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSimpleRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROCUREMENT_ITEM_NOT_EXISTS;

/**
 * 项目采购单申请明细 Service 实现类
 */
@Service
@Validated
public class ProcurementItemServiceImpl implements ProcurementItemService {

    @Resource
    private ProcurementItemRepository procurementItemRepository;

    @Resource
    private ProcurementItemOnlyRepository procurementItemOnlyRepository;

    @Resource
    private ProcurementItemMapper procurementItemMapper;

    @Resource
    private InventoryStoreLogRepository inventoryStoreLogRepository;

    @Resource
    private InventoryRoomRepository inventoryRoomRepository;

    @Resource
    private ProjectSimpleRepository projectSimpleRepository;

    @Override
    public Long createProcurementItem(ProcurementItemCreateReqVO createReqVO) {
        // 插入
        ProcurementItem procurementItem = procurementItemMapper.toEntity(createReqVO);
        procurementItemRepository.save(procurementItem);
        // 返回
        return procurementItem.getId();
    }

    @Override
    public void updateProcurementItem(ProcurementItemUpdateReqVO updateReqVO) {
        // 校验存在
        validateProcurementItemExists(updateReqVO.getId());
        // 更新
        ProcurementItem updateObj = procurementItemMapper.toEntity(updateReqVO);
        procurementItemRepository.save(updateObj);
    }

    @Override
    public void updateProcurementItemFocusStatus(Long id, Integer focusStatus) {
        // 校验存在
        ProcurementItem procurementItem = procurementItemRepository.findById(id)
                .orElseThrow(() -> exception(PROCUREMENT_ITEM_NOT_EXISTS));
        // 更新关注状态
        procurementItem.setFocusStatus(focusStatus);
        procurementItemRepository.save(procurementItem);
    }

    @Override
    public void deleteProcurementItem(Long id) {
        // 校验存在
        validateProcurementItemExists(id);
        // 删除
        procurementItemRepository.deleteById(id);
    }

    private void validateProcurementItemExists(Long id) {
        procurementItemRepository.findById(id).orElseThrow(() -> exception(PROCUREMENT_ITEM_NOT_EXISTS));
    }

    @Override
    public Optional<ProcurementItem> getProcurementItem(Long id) {
        return procurementItemRepository.findById(id);
    }

    @Override
    public List<ProcurementItem> getProcurementItemList(Collection<Long> ids) {
        return StreamSupport.stream(procurementItemRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProcurementItem> getProcurementItemPage(ProcurementItemPageReqVO pageReqVO, ProcurementItemPageOrder orderV0) {
        Specification<ProcurementItem> spec = getSpecification(pageReqVO);
        List<ProcurementItem> content = null;
        long totalElements = 0;
        
        // 如果有validDate排序，需要特殊处理空字符串
        if (orderV0.getValidDate() != null) {
            // 先查询所有符合条件的数据
            List<ProcurementItem> allContent = procurementItemRepository.findAll(spec);
            totalElements = allContent.size();
            
            // 手动排序（将空字符串和NULL都视为最后）
            allContent = sortWithNullsAndEmptyLast(allContent, orderV0);
            
            // 手动分页
            if(pageReqVO.getPageNo() != -1){
                int start = (pageReqVO.getPageNo() - 1) * pageReqVO.getPageSize();
                int end = Math.min(start + pageReqVO.getPageSize(), allContent.size());
                content = start < allContent.size() ? allContent.subList(start, end) : new ArrayList<>();
            } else {
                content = allContent;
            }
        } else {
            // 没有validDate排序，使用标准的JPA排序
            Sort sort = createSort(orderV0);
            
            if(pageReqVO.getPageNo() != -1){
                Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);
                Page<ProcurementItem> page = procurementItemRepository.findAll(spec, pageable);
                totalElements = page.getTotalElements();
                content = page.getContent();
            }else{
                content = procurementItemRepository.findAll(spec, sort);
                totalElements = content.size();
            }
        }

        // 转换为 PageResult 并返回
        return new PageResult<>(content, totalElements);
    }
    
    /**
     * 自定义排序：将NULL和空字符串都排在最后
     */
    private List<ProcurementItem> sortWithNullsAndEmptyLast(List<ProcurementItem> list, ProcurementItemPageOrder order) {
        return list.stream()
            .sorted((a, b) -> {
                // 1. 先按关注状态排序
                if (order.getFocusStatus() != null) {
                    int focusCompare = compareFocusStatus(a.getFocusStatus(), b.getFocusStatus(), order.getFocusStatus());
                    if (focusCompare != 0) return focusCompare;
                }
                
                // 2. 再按有效期排序（空字符串和NULL都排最后）
                if (order.getValidDate() != null) {
                    int dateCompare = compareValidDate(a.getValidDate(), b.getValidDate(), order.getValidDate());
                    if (dateCompare != 0) return dateCompare;
                }
                
                // 3. 最后按创建时间排序
                return b.getCreateTime().compareTo(a.getCreateTime());
            })
            .collect(Collectors.toList());
    }
    
    private int compareFocusStatus(Integer a, Integer b, String direction) {
        if (a == null && b == null) return 0;
        if (a == null) return 1;
        if (b == null) return -1;
        return "asc".equals(direction) ? a.compareTo(b) : b.compareTo(a);
    }
    
    private int compareValidDate(String a, String b, String direction) {
        // 空字符串和NULL都视为"无值"，排在最后
        boolean aIsEmpty = (a == null || a.trim().isEmpty());
        boolean bIsEmpty = (b == null || b.trim().isEmpty());
        
        if (aIsEmpty && bIsEmpty) return 0;
        if (aIsEmpty) return 1;  // a是空值，排在后面
        if (bIsEmpty) return -1; // b是空值，排在后面
        
        // 都有值，按字符串比较（日期字符串可以直接比较）
        return "asc".equals(direction) ? a.compareTo(b) : b.compareTo(a);
    }

    @Override
    public PageResult<ProcurementItemOnly> getProcurementItemPageSimple(ProcurementItemPageReqVO pageReqVO, ProcurementItemPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<ProcurementItemOnly> spec = getSpecification(pageReqVO);

        // 执行查询
        Page<ProcurementItemOnly> page = procurementItemOnlyRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @NotNull
    private static <T>Specification<T> getSpecification(ProcurementItemPageReqVO pageReqVO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("source"), pageReqVO.getSource()));

            if (pageReqVO.getRoomIds() != null) {
                predicates.add(root.get("receiveRoomId").in(pageReqVO.getRoomIds()));
            }

            if (pageReqVO.getCreateTimeV() != null) {
                predicates.add(cb.between(root.get("createTime"), pageReqVO.getCreateTimeV()[0], pageReqVO.getCreateTimeV()[1]));
            }

            if (pageReqVO.getPurchaseAcceptTime() != null) {
                predicates.add(cb.between(root.get("purchaseAcceptTime"), pageReqVO.getPurchaseAcceptTime()[0], pageReqVO.getPurchaseAcceptTime()[1]));
            }

            if(pageReqVO.getOnlyHasStock()!=null&&pageReqVO.getOnlyHasStock()){
                // inedQuantity大于outedQuantity的绝对值
                predicates.add(cb.gt(
                        root.get("inedQuantity"),
                        cb.abs(root.get("outedQuantity"))
                ));
            }

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getProcurementId() != null) {
                predicates.add(cb.equal(root.get("procurementId"), pageReqVO.getProcurementId()));
            }

            if (pageReqVO.getProcurementType() != null) {
                predicates.add(cb.equal(root.get("procurementType"), pageReqVO.getProcurementType()));
            }

            if (pageReqVO.getProjectSupplyId() != null) {
                predicates.add(cb.equal(root.get("projectSupplyId"), pageReqVO.getProjectSupplyId()));
            }

            if (pageReqVO.getReceiveRoomId() != null) {
                predicates.add(cb.equal(root.get("receiveRoomId"), pageReqVO.getReceiveRoomId()));
            }

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getFeeStandard() != null) {
                predicates.add(cb.equal(root.get("feeStandard"), pageReqVO.getFeeStandard()));
            }

            if (pageReqVO.getUnitFee() != null) {
                predicates.add(cb.equal(root.get("unitFee"), pageReqVO.getUnitFee()));
            }

            if (pageReqVO.getUnitAmount() != null) {
                predicates.add(cb.equal(root.get("unitAmount"), pageReqVO.getUnitAmount()));
            }

            if (pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if (pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if (pageReqVO.getBuyPrice() != null) {
                predicates.add(cb.equal(root.get("buyPrice"), pageReqVO.getBuyPrice()));
            }

            if (pageReqVO.getSalePrice() != null) {
                predicates.add(cb.equal(root.get("salePrice"), pageReqVO.getSalePrice()));
            }

            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            // 只有当 validDate 是有效的日期范围数组时才添加查询条件
            if (pageReqVO.getValidDate() != null && pageReqVO.getValidDate().length == 2) {
                predicates.add(cb.between(root.get("validDate"), pageReqVO.getValidDate()[0], pageReqVO.getValidDate()[1]));
            }
            if (pageReqVO.getBrand() != null) {
                predicates.add(cb.equal(root.get("brand"), pageReqVO.getBrand()));
            }

            if (pageReqVO.getCatalogNumber() != null) {
                predicates.add(cb.equal(root.get("catalogNumber"), pageReqVO.getCatalogNumber()));
            }

            if (pageReqVO.getDeliveryDate() != null) {
                predicates.add(cb.between(root.get("deliveryDate"), pageReqVO.getDeliveryDate()[0], pageReqVO.getDeliveryDate()[1]));
            }

            if (pageReqVO.getStatus() != null) {

                if (pageReqVO.getStatus().equals(ProcurementItemStatusEnums.PART_STORAGE.getStatus())) {
                    // 查询 inedQuantity>0 && inedQuantity<quantity
                    predicates.add(cb.and(cb.greaterThan(root.get("inedQuantity"), BigDecimal.ZERO), cb.lessThan(root.get("inedQuantity"), root.get("quantity"))));
                } else if (pageReqVO.getStatus().equals(ProcurementItemStatusEnums.ALL_STORAGE.getStatus())) {
                    // 查询 inedQuantity>=quantity
                    predicates.add(cb.and(cb.greaterThan(root.get("inedQuantity"), BigDecimal.ZERO),cb.greaterThanOrEqualTo(root.get("inedQuantity"), root.get("quantity"))));
                } else {
                    predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
                }

            }else{
                predicates.add(cb.notEqual(root.get("status"), ProcurementItemStatusEnums.CANCEL.getStatus()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public List<ProcurementItem> getProcurementItemList(ProcurementItemPageReqVO exportReqVO) {
        Sort sort = createSort(new ProcurementItemPageOrder());
        // 执行查询
        // 步骤1: 查询所有的ProcurementItem
        List<ProcurementItem> all = procurementItemRepository.findAll(getSpecification(exportReqVO), sort);

        // 步骤2: 提取唯一的receiveRoomId
        Set<Long> receiveRoomIds = all.stream()
                .map(ProcurementItem::getReceiveRoomId)
                .collect(Collectors.toSet());


        Set<Long> projectIds = all.stream()
                .map(ProcurementItem::getProjectId)
                .collect(Collectors.toSet());


        // 步骤3: 使用in查询获取所有的receiveRoomList
        List<InventoryRoom> receiveRoomList = inventoryRoomRepository.findAllById(receiveRoomIds);

        List<ProjectSimple> projectSimpleList = projectSimpleRepository.findAllById(projectIds);

        // 步骤4: 构建一个映射，从receiveRoomId到InventoryRoom
        Map<Long, InventoryRoom> receiveRoomIdToInventoryRoomMap = receiveRoomList.stream()
                .collect(Collectors.toMap(InventoryRoom::getId, Function.identity()));

        Map<Long, ProjectSimple> projectSimpleMap = projectSimpleList.stream()
                .collect(Collectors.toMap(ProjectSimple::getId, Function.identity()));

        // 步骤5: 给ProcurementItem赋值purchaseManagerName
        for (ProcurementItem procurementItem : all) {
            InventoryRoom inventoryRoom = receiveRoomIdToInventoryRoomMap.get(procurementItem.getReceiveRoomId());
            if (inventoryRoom != null && inventoryRoom.getManager() != null) {
                procurementItem.setPurchaseManagerName(inventoryRoom.getManager().getNickname());
            }
            ProjectSimple projectSimple = projectSimpleMap.get(procurementItem.getProjectId());
            if(projectSimple!=null&& projectSimple.getSales()!=null){
                procurementItem.setSalesName(projectSimple.getSales().getNickname());
            }
            if(projectSimple!=null&& projectSimple.getManager()!=null){
                procurementItem.setCustomerName(projectSimple.getManager().getNickname());
            }
        }

        return all;
    }

    private Sort createSort(ProcurementItemPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 1. 优先按关注状态排序（关注的在前）
        if (order.getFocusStatus() != null) {
            orders.add(new Sort.Order(
                order.getFocusStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, 
                "focusStatus"
            ));
        }

        // 2. 有效期排序（NULL和空字符串都排在最后）
        // 注意：validDate是String类型，需要同时处理NULL和空字符串
        if (order.getValidDate() != null) {
            Sort.Direction direction = order.getValidDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            // 先对NULL和空值进行排序，然后对有值的进行正常排序
            // JPA的nullsLast()只处理NULL，不处理空字符串
            // 因此我们需要确保数据库中空字符串也被视为NULL
            orders.add(new Sort.Order(direction, "validDate")
                .nullsLast()  // NULL值在最后
                .ignoreCase() // 忽略大小写（对字符串排序有效）
            );
        }

        // 3. 最后按创建时间排序（作为兜底排序，保证结果稳定）
        // 注意：只有在没有指定其他排序时，createTime才会起作用
        if (order.getCreateTime() != null) {
            orders.add(new Sort.Order(
                order.getCreateTime().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, 
                "createTime"
            ));
        } else {
            // 默认按创建时间降序
            orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        }

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getProcurementId() != null) {
            orders.add(new Sort.Order(order.getProcurementId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "procurementId"));
        }

        if (order.getProjectSupplyId() != null) {
            orders.add(new Sort.Order(order.getProjectSupplyId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectSupplyId"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getFeeStandard() != null) {
            orders.add(new Sort.Order(order.getFeeStandard().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feeStandard"));
        }

        if (order.getUnitFee() != null) {
            orders.add(new Sort.Order(order.getUnitFee().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "unitFee"));
        }

        if (order.getUnitAmount() != null) {
            orders.add(new Sort.Order(order.getUnitAmount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "unitAmount"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getBuyPrice() != null) {
            orders.add(new Sort.Order(order.getBuyPrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "buyPrice"));
        }

        if (order.getSalePrice() != null) {
            orders.add(new Sort.Order(order.getSalePrice().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "salePrice"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        // validDate 已在上面处理，这里不再重复添加

        if (order.getBrand() != null) {
            orders.add(new Sort.Order(order.getBrand().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "brand"));
        }

        if (order.getCatalogNumber() != null) {
            orders.add(new Sort.Order(order.getCatalogNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "catalogNumber"));
        }

        if (order.getDeliveryDate() != null) {
            orders.add(new Sort.Order(order.getDeliveryDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "deliveryDate"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }

    @Transactional
    public void updateStockQuantity(Long sourceItemId) {
        List<InventoryStoreLog> bySourceItemId = inventoryStoreLogRepository.findBySourceItemId(sourceItemId);
        BigDecimal inedSum = BigDecimal.ZERO;
        BigDecimal outedSum = BigDecimal.ZERO;

        if (bySourceItemId != null && !bySourceItemId.isEmpty()) {
            for (InventoryStoreLog log : bySourceItemId) {
                if (log.getChangeNum().compareTo(BigDecimal.ZERO) > 0) {
                    inedSum = inedSum.add(log.getChangeNum());
                } else {
                    outedSum = outedSum.add(log.getChangeNum());
                }
            }
        }

        procurementItemOnlyRepository.updateInedQuantityAndOutedQuantityById(inedSum, outedSum, sourceItemId);
    }
}
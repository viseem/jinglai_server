package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.enums.InventoryCheckInTypeEnums;
import cn.iocoder.yudao.module.jl.enums.InventoryStoreInTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProcurementStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.project.SupplyPickupItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryCheckInRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryStoreInRepository;
import cn.iocoder.yudao.module.jl.repository.project.SupplyPickupItemRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
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
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.SupplyPickupMapper;
import cn.iocoder.yudao.module.jl.repository.project.SupplyPickupRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 取货单申请 Service 实现类
 */
@Service
@Validated
public class SupplyPickupServiceImpl implements SupplyPickupService {

    @Resource
    private SupplyPickupRepository supplyPickupRepository;

    @Resource
    private InventoryStoreInRepository inventoryStoreInRepository;

    @Resource
    private InventoryCheckInRepository inventoryCheckInRepository;

    @Resource
    private SupplyPickupMapper supplyPickupMapper;

    @Resource
    private SupplyPickupItemRepository supplyPickupItemRepository;

    @Resource
    private SupplyPickupItemMapper supplyPickupItemMapper;

    @Override
    public Long createSupplyPickup(SupplyPickupCreateReqVO createReqVO) {
        // 插入
        SupplyPickup supplyPickup = supplyPickupMapper.toEntity(createReqVO);
        supplyPickupRepository.save(supplyPickup);
        // 返回
        return supplyPickup.getId();
    }

    @Override
    public void updateSupplyPickup(SupplyPickupUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplyPickupExists(updateReqVO.getId());
        // 更新
        SupplyPickup updateObj = supplyPickupMapper.toEntity(updateReqVO);
        supplyPickupRepository.save(updateObj);
    }

    @Override
    public void deleteSupplyPickup(Long id) {
        // 校验存在
        validateSupplyPickupExists(id);
        supplyPickupItemRepository.deleteBySupplyPickupId(id);
        // 删除
        supplyPickupRepository.deleteById(id);
    }

    private void validateSupplyPickupExists(Long id) {
        supplyPickupRepository.findById(id).orElseThrow(() -> exception(SUPPLY_PICKUP_NOT_EXISTS));
    }

    @Override
    public Optional<SupplyPickup> getSupplyPickup(Long id) {
        return supplyPickupRepository.findById(id);
    }

    @Override
    public List<SupplyPickup> getSupplyPickupList(Collection<Long> ids) {
        return StreamSupport.stream(supplyPickupRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SupplyPickup> getSupplyPickupPage(SupplyPickupPageReqVO pageReqVO, SupplyPickupPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SupplyPickup> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if (pageReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + pageReqVO.getCode() + "%"));
            }

            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
            }

            if (pageReqVO.getShipmentCodes() != null) {
                predicates.add(cb.like(root.get("shipmentCodes"), "%" + pageReqVO.getShipmentCodes() + "%"));
            }

            if (Objects.equals(pageReqVO.getQueryStatus(), ProcurementStatusEnums.WAITING_CHECK_IN.toString())) {
                predicates.add(cb.equal(root.get("waitCheckIn"), true));
            }

            if (Objects.equals(pageReqVO.getQueryStatus(), ProcurementStatusEnums.WAITING_IN.toString())) {
                predicates.add(cb.equal(root.get("waitStoreIn"), true));
            }


            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if (pageReqVO.getSendDate() != null) {
                predicates.add(cb.between(root.get("sendDate"), pageReqVO.getSendDate()[0], pageReqVO.getSendDate()[1]));
            }
            if (pageReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), pageReqVO.getUserId()));
            }

            if (pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if (pageReqVO.getContactName() != null) {
                predicates.add(cb.like(root.get("contactName"), "%" + pageReqVO.getContactName() + "%"));
            }

            if (pageReqVO.getContactPhone() != null) {
                predicates.add(cb.equal(root.get("contactPhone"), pageReqVO.getContactPhone()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SupplyPickup> page = supplyPickupRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SupplyPickup> getSupplyPickupList(SupplyPickupExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SupplyPickup> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (exportReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), exportReqVO.getProjectId()));
            }

            if (exportReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), exportReqVO.getProjectCategoryId()));
            }

            if (exportReqVO.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), exportReqVO.getCode()));
            }

            if (exportReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), exportReqVO.getStatus()));
            }


            if (exportReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), exportReqVO.getMark()));
            }

            if (exportReqVO.getSendDate() != null) {
                predicates.add(cb.between(root.get("sendDate"), exportReqVO.getSendDate()[0], exportReqVO.getSendDate()[1]));
            }
            if (exportReqVO.getUserId() != null) {
                predicates.add(cb.equal(root.get("userId"), exportReqVO.getUserId()));
            }

            if (exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if (exportReqVO.getContactName() != null) {
                predicates.add(cb.like(root.get("contactName"), "%" + exportReqVO.getContactName() + "%"));
            }

            if (exportReqVO.getContactPhone() != null) {
                predicates.add(cb.equal(root.get("contactPhone"), exportReqVO.getContactPhone()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return supplyPickupRepository.findAll(spec);
    }

    /**
     * @param saveReqVO
     */
    @Override
    public void saveSupplyPickup(SupplyPickupSaveReqVO saveReqVO) {
        if (saveReqVO.getId() != null) {
            // 存在 id，更新操作
            Long id = saveReqVO.getId();
            // 校验存在
            validateSupplyPickupExists(id);
        }

        // 更新或新建
        SupplyPickup supplyPickup = supplyPickupMapper.toEntity(saveReqVO);
        supplyPickup.setWaitCheckIn(true); // 代签收
        supplyPickup.setCode(String.valueOf(Instant.now().toEpochMilli()));
        supplyPickupRepository.save(supplyPickup);
        Long pickupId = supplyPickup.getId();

        // 删除原有的
        supplyPickupItemRepository.deleteBySupplyPickupId(pickupId);

        // 更新 items
        supplyPickupItemRepository.saveAll(saveReqVO.getItems().stream().map(item -> {
            item.setSupplyPickupId(pickupId);
            return supplyPickupItemMapper.toEntity(item);
        }).collect(Collectors.toList()));

    }

    /**
     * @param saveReqVO
     */
    @Override
    public void checkIn(PickupCheckInReqVO saveReqVO) {
        // 校验存在
        validateSupplyPickupExists(saveReqVO.getPickupId());

        // 根据 saveReqVo 的 list ，遍历每一项
        if (saveReqVO.getList() != null && saveReqVO.getList().size() > 0) {
            AtomicBoolean allCheckIn = new AtomicBoolean(true);

            saveReqVO.getList().forEach(checkIn -> {
                Long projectSupplyId = checkIn.getProjectSupplyId();
                String status = checkIn.getStatus();
                Integer checkInQuantity = checkIn.getCheckInNum();
                SupplyPickupItem item = supplyPickupItemRepository.findBySupplyPickupIdAndProjectSupplyId(saveReqVO.getPickupId(), projectSupplyId);
                if (item != null) {
                    checkInQuantity += item.getCheckInQuantity();

                    if (checkInQuantity < item.getQuantity()) {
                        // 还有需要签收的子项
                        allCheckIn.set(false);
                    }

                    item.setCheckInQuantity(checkInQuantity);
                    item.setStatus(status);
                    supplyPickupItemRepository.save(item);

                    // 保存签收日志
                    InventoryCheckIn checkInLog = new InventoryCheckIn();
                    checkInLog.setProjectSupplyId(item.getProjectSupplyId());
                    checkInLog.setInQuantity(checkIn.getCheckInNum());
                    checkInLog.setType(InventoryCheckInTypeEnums.PROCUREMENT.toString());
                    checkInLog.setMark(checkIn.getMark());
                    checkInLog.setStatus(checkIn.getStatus());
                    checkInLog.setRefId(saveReqVO.getPickupId());
                    checkInLog.setRefItemId(item.getId());
                    inventoryCheckInRepository.save(checkInLog);
                }
            });

            supplyPickupRepository.findById(saveReqVO.getPickupId()).ifPresent(pickup -> {
                pickup.setWaitCheckIn(!allCheckIn.get());
                pickup.setWaitStoreIn(true);
                supplyPickupRepository.save(pickup);
            });
        }
    }

    /**
     * @param saveReqVO
     */
    @Override
    public void storeIn(StoreInPickupItemReqVO saveReqVO) {
        // 校验存在
        validateSupplyPickupExists(saveReqVO.getPickupId());

        // 更新采购单项的状态
        // 根据 saveReqVo 的 list，遍历每一项，去更新采购单项的状态
        if (saveReqVO.getList() != null && saveReqVO.getList().size() > 0) {
            AtomicBoolean allStoreIn = new AtomicBoolean(true);

            saveReqVO.getList().forEach(storeIn -> {
                Long projectSupplyId = storeIn.getProjectSupplyId();
                String status = storeIn.getStatus();
                Integer storeInQuantity = storeIn.getInNum();
                SupplyPickupItem item = supplyPickupItemRepository.findBySupplyPickupIdAndProjectSupplyId(saveReqVO.getPickupId(), projectSupplyId);
                if (item != null) {
                    storeInQuantity += item.getInQuantity();

                    if (storeInQuantity < item.getCheckInQuantity()) {
                        // 还有需要入库的子项
                        allStoreIn.set(false);
                    }

                    item.setInQuantity(storeInQuantity);
                    item.setStatus(status);
                    item.setRoomId(storeIn.getRoomId());
                    item.setContainerId(storeIn.getContainerId());
                    item.setLocationName(storeIn.getLocationName());
                    item.setPlaceId(storeIn.getPlaceId());
                    item.setTemperature(storeIn.getTemperature());
                    item.setValidDate(storeIn.getValidDate());

                    supplyPickupItemRepository.save(item);

                    // 保存入库日志
                    InventoryStoreIn storeInLog = new InventoryStoreIn();
                    storeInLog.setProjectSupplyId(item.getProjectSupplyId());
                    storeInLog.setInQuantity(storeIn.getInNum());
                    storeInLog.setType(InventoryStoreInTypeEnums.PROCUREMENT.toString());
                    storeInLog.setMark(storeIn.getMark());
                    storeInLog.setStatus(storeIn.getStatus());
                    storeInLog.setRefId(saveReqVO.getPickupId());
                    storeInLog.setRefItemId(item.getId());
                    storeInLog.setRoomId(storeIn.getRoomId());
                    storeInLog.setContainerId(storeIn.getContainerId());
                    storeInLog.setPlaceId(storeIn.getPlaceId());
                    storeInLog.setTemperature(storeIn.getTemperature());
                    storeInLog.setValidDate(storeIn.getValidDate());
                    inventoryStoreInRepository.save(storeInLog);

                }

            });

            // 更新采购单的待入库状态
            supplyPickupRepository.updateWaitStoreInById(saveReqVO.getPickupId(), !allStoreIn.get());
        }

    }

    private Sort createSort(SupplyPickupPageOrder order) {
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

        if (order.getProjectCategoryId() != null) {
            orders.add(new Sort.Order(order.getProjectCategoryId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectCategoryId"));
        }

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }

        if (order.getStatus() != null) {
            orders.add(new Sort.Order(order.getStatus().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "status"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getSendDate() != null) {
            orders.add(new Sort.Order(order.getSendDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "sendDate"));
        }

        if (order.getUserId() != null) {
            orders.add(new Sort.Order(order.getUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "userId"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getContactName() != null) {
            orders.add(new Sort.Order(order.getContactName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contactName"));
        }

        if (order.getContactPhone() != null) {
            orders.add(new Sort.Order(order.getContactPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "contactPhone"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
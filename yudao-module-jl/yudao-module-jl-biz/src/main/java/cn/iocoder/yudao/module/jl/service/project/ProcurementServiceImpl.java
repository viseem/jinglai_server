package cn.iocoder.yudao.module.jl.service.project;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import cn.iocoder.yudao.module.jl.enums.InventoryCheckInTypeEnums;
import cn.iocoder.yudao.module.jl.enums.InventoryStoreInTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProcurementStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementItemMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementPaymentMapper;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementShipmentMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryCheckInRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryStoreInRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementPaymentRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementShipmentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.Predicate;

import java.util.*;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.jl.mapper.project.ProcurementMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目采购单申请 Service 实现类
 */
@Service
@Validated
public class ProcurementServiceImpl implements ProcurementService {

    @Resource
    private ProcurementRepository procurementRepository;

    @Resource
    private ProcurementMapper procurementMapper;

    @Resource
    private ProcurementItemRepository procurementItemRepository;

    @Resource
    private ProcurementItemMapper procurementItemMapper;

    @Resource
    private ProcurementShipmentRepository procurementShipmentRepository;

    @Resource
    private ProcurementShipmentMapper procurementShipmentMapper;

    @Resource
    private ProcurementPaymentRepository procurementPaymentRepository;

    @Resource
    private InventoryCheckInRepository inventoryCheckInRepository;

    @Resource
    private InventoryStoreInRepository inventoryStoreInRepository;


    @Resource
    private ProcurementPaymentMapper procurementPaymentMapper;

    @Override
    public Long createProcurement(ProcurementCreateReqVO createReqVO) {
        // 插入
        Procurement procurement = procurementMapper.toEntity(createReqVO);
        procurementRepository.save(procurement);
        // 返回
        return procurement.getId();
    }

    @Override
    public void updateProcurement(ProcurementUpdateReqVO updateReqVO) {
        // 校验存在
        validateProcurementExists(updateReqVO.getId());

        // 更新
        Procurement updateObj = procurementMapper.toEntity(updateReqVO);
        updateObj = procurementRepository.save(updateObj);
    }

    /**
     * 全量更新采购单
     *
     * @param saveReqVO
     */
    @Override
    public void saveProcurement(ProcurementSaveReqVO saveReqVO) {
        if (saveReqVO.getId() != null) {
            // 存在 id，更新操作
            Long id = saveReqVO.getId();
            // 校验存在
            validateProcurementExists(id);
        }

        // 更新或者创建
        Procurement updateObj = procurementMapper.toEntity(saveReqVO);
        String dateStr = DateUtil.format(new Date(), "yyyyMMdd");
        long count = procurementRepository.countByProjectId(saveReqVO.getProjectId());
        updateObj.setCode(dateStr + "-" + updateObj.getProjectId() + "-" + count);
        updateObj.setWaitCheckIn(true);// 可以在check in列表中看到
        updateObj = procurementRepository.save(updateObj);
        Long procurementId = updateObj.getId();

        // 删除原有的采购单明细
        procurementItemRepository.deleteByProcurementId(procurementId);

        // 创建采购单明细
        procurementItemMapper.toEntityList(saveReqVO.getItems()).forEach(procurementItem -> {
            procurementItem.setProcurementId(procurementId);
            procurementItemRepository.save(procurementItem);
        });

    }

    @Override
    public void deleteProcurement(Long id) {
        // 校验存在
        validateProcurementExists(id);
        // 删除
        procurementRepository.deleteById(id);
    }

    private void validateProcurementExists(Long id) {
        procurementRepository.findById(id).orElseThrow(() -> exception(PROCUREMENT_NOT_EXISTS));
    }

    @Override
    public Optional<Procurement> getProcurement(Long id) {
        return procurementRepository.findById(id);
    }

    @Override
    public List<Procurement> getProcurementList(Collection<Long> ids) {
        return StreamSupport.stream(procurementRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<Procurement> getProcurementPage(ProcurementPageReqVO pageReqVO, ProcurementPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<Procurement> spec = (root, query, cb) -> {
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

            if (pageReqVO.getShipmentCodes() != null) {
                predicates.add(cb.like(root.get("shipmentCodes"), "%" + pageReqVO.getShipmentCodes() + "%"));
            }


            if (pageReqVO.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), pageReqVO.getStatus()));
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

            if (pageReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getStartDate()[0], pageReqVO.getStartDate()[1]));
            }
            if (pageReqVO.getCheckUserId() != null) {
                predicates.add(cb.equal(root.get("checkUserId"), pageReqVO.getCheckUserId()));
            }

            if (pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if (pageReqVO.getReceiverUserId() != null) {
                predicates.add(cb.equal(root.get("receiverUserId"), pageReqVO.getReceiverUserId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<Procurement> page = procurementRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<Procurement> getProcurementList(ProcurementExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<Procurement> spec = (root, query, cb) -> {
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

            if (exportReqVO.getStartDate() != null) {
                predicates.add(cb.between(root.get("startDate"), exportReqVO.getStartDate()[0], exportReqVO.getStartDate()[1]));
            }
            if (exportReqVO.getCheckUserId() != null) {
                predicates.add(cb.equal(root.get("checkUserId"), exportReqVO.getCheckUserId()));
            }

            if (exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if (exportReqVO.getReceiverUserId() != null) {
                predicates.add(cb.equal(root.get("receiverUserId"), exportReqVO.getReceiverUserId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return procurementRepository.findAll(spec);
    }

    /**
     * @param saveReqVO
     */
    @Override
    public void saveShipments(ProcurementUpdateShipmentsReqVO saveReqVO) {
        // 校验存在
        validateProcurementExists(saveReqVO.getProcurementId());

        // 删除先前的物流信息
        procurementShipmentRepository.deleteByProcurementId(saveReqVO.getProcurementId());

        // 写入新的物流信息
        if (saveReqVO.getShipments() != null && saveReqVO.getShipments().size() > 0) {
            List<ProcurementShipment> shipments = saveReqVO.getShipments().stream().map(shipment -> {
                shipment.setProcurementId(saveReqVO.getProcurementId());

                return procurementShipmentMapper.toEntity(shipment);
            }).collect(Collectors.toList());


            procurementShipmentRepository.saveAll(shipments);

            // 遍历 shipments 中的 getShipmentNumber()，并且拼成字符串
            String shipmentNumbers = shipments.stream().map(ProcurementShipment::getShipmentNumber).collect(Collectors.joining(","));
            // 更新采购单的物流信息
            procurementRepository.findById(saveReqVO.getProcurementId()).ifPresent(procurement -> {
                procurement.setShipmentCodes(procurement.getShipmentCodes() + "," + shipmentNumbers);
                procurement.setStatus(ProcurementStatusEnums.WAITING_CHECK_IN.toString());
                procurementRepository.save(procurement);
            });
        }
    }

    /**
     * @param saveReqVO
     */
    @Override
    public void savePayments(ProcurementUpdatePaymentsReqVO saveReqVO) {
        // 校验存在
        validateProcurementExists(saveReqVO.getProcurementId());

        // 删除先前的付款信息
        procurementPaymentRepository.deleteByProcurementId(saveReqVO.getProcurementId());

        // 写入新的物流信息
        if (saveReqVO.getPayments() != null && saveReqVO.getPayments().size() > 0) {
            List<ProcurementPayment> payments = saveReqVO.getPayments().stream().map(payment -> {
                payment.setProcurementId(saveReqVO.getProcurementId());
                return procurementPaymentMapper.toEntity(payment);
            }).collect(Collectors.toList());

            procurementPaymentRepository.saveAll(payments);
        }

        // 更新状态
        procurementRepository.updateStatusById(saveReqVO.getProcurementId(), ProcurementStatusEnums.WAITING_START_PROCUREMENT.toString());
    }

    /**
     * 签收
     *
     * @param saveReqVO
     */
    @Override
    public void checkIn(ProcurementShipmentCheckInReqVO saveReqVO) {
        // 校验存在
        validateProcurementExists(saveReqVO.getProcurementId());

        // 更新采购单项的状态
        // 根据 saveReqVo 的 list ，遍历每一项，去更新采购单项的状态
        if (saveReqVO.getList() != null && saveReqVO.getList().size() > 0) {
            AtomicBoolean allCheckIn = new AtomicBoolean(true);

            saveReqVO.getList().forEach(checkIn -> {
                Long projectSupplyId = checkIn.getProjectSupplyId();
                String status = checkIn.getStatus();
                Integer checkInQuantity = checkIn.getCheckInNum();
                ProcurementItem item = procurementItemRepository.findByProcurementIdAndProjectSupplyId(saveReqVO.getProcurementId(), projectSupplyId);
                if (item != null) {
                    checkInQuantity += item.getCheckInQuantity();


                    if (checkInQuantity < item.getQuantity()) {
                        // 还有需要签收的子项
                        allCheckIn.set(false);
                    }

                    item.setCheckInQuantity(checkInQuantity);
                    item.setStatus(status);
                    procurementItemRepository.save(item);

                    // 保存签收日志
                    InventoryCheckIn checkInLog = new InventoryCheckIn();
                    checkInLog.setProjectSupplyId(item.getProjectSupplyId());
                    checkInLog.setInQuantity(checkIn.getCheckInNum());
                    checkInLog.setType(InventoryCheckInTypeEnums.PROCUREMENT.toString());
                    checkInLog.setMark(checkIn.getMark());
                    checkInLog.setStatus(checkIn.getStatus());
                    checkInLog.setRefId(saveReqVO.getProcurementId());
                    checkInLog.setRefItemId(item.getId());
                    inventoryCheckInRepository.save(checkInLog);
                }
            });

            // 更新采购单的待签收转态
            procurementRepository.updateWaitCheckInById(saveReqVO.getProcurementId(), !allCheckIn.get());
            procurementRepository.updateWaitStoreInById(saveReqVO.getProcurementId(), true);
        }


    }

    /**
     * @param saveReqVO
     */

    /**
     * 入库
     *
     * @param saveReqVO
     */
    @Override
    public void storeIn(StoreInProcurementItemReqVO saveReqVO) {
        // 校验存在
        validateProcurementExists(saveReqVO.getProcurementId());

        // 更新采购单项的状态
        // 根据 saveReqVo 的 list ，遍历每一项，去更新采购单项的状态
        if (saveReqVO.getList() != null && saveReqVO.getList().size() > 0) {
            AtomicBoolean allStoreIn = new AtomicBoolean(true);

            saveReqVO.getList().forEach(storeIn -> {
                Long projectSupplyId = storeIn.getProjectSupplyId();
                String status = storeIn.getStatus();
                Integer storeInQuantity = storeIn.getInNum();
                ProcurementItem item = procurementItemRepository.findByProcurementIdAndProjectSupplyId(saveReqVO.getProcurementId(), projectSupplyId);
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
                    item.setPlaceId(storeIn.getPlaceId());
                    item.setTemperature(storeIn.getTemperature());
                    item.setValidDate(storeIn.getValidDate());

                    procurementItemRepository.save(item);

                    // 保存入库日志
                    InventoryStoreIn storeInLog = new InventoryStoreIn();
                    storeInLog.setProjectSupplyId(item.getProjectSupplyId());
                    storeInLog.setInQuantity(storeIn.getInNum());
                    storeInLog.setType(InventoryStoreInTypeEnums.PROCUREMENT.toString());
                    storeInLog.setMark(storeIn.getMark());
                    storeInLog.setStatus(storeIn.getStatus());
                    storeInLog.setRefId(saveReqVO.getProcurementId());
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
            procurementRepository.updateWaitStoreInById(saveReqVO.getProcurementId(), !allStoreIn.get());


        }

    }

    private Sort createSort(ProcurementPageOrder order) {
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

        if (order.getStartDate() != null) {
            orders.add(new Sort.Order(order.getStartDate().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "startDate"));
        }

        if (order.getCheckUserId() != null) {
            orders.add(new Sort.Order(order.getCheckUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "checkUserId"));
        }

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getReceiverUserId() != null) {
            orders.add(new Sort.Order(order.getReceiverUserId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiverUserId"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
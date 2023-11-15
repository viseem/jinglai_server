package cn.iocoder.yudao.module.jl.service.project;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.enums.InventoryCheckInTypeEnums;
import cn.iocoder.yudao.module.jl.enums.InventoryStoreInTypeEnums;
import cn.iocoder.yudao.module.jl.enums.ProcurementStatusEnums;
import cn.iocoder.yudao.module.jl.mapper.project.SupplySendInItemMapper;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryCheckInRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.InventoryStoreInRepository;
import cn.iocoder.yudao.module.jl.repository.project.SupplySendInItemRepository;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
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

import cn.iocoder.yudao.module.jl.mapper.project.SupplySendInMapper;
import cn.iocoder.yudao.module.jl.repository.project.SupplySendInRepository;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.*;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.PROCUREMENT_CODE_DEFAULT_PREFIX;

/**
 * 物资寄来单申请 Service 实现类
 */
@Service
@Validated
public class SupplySendInServiceImpl implements SupplySendInService {

    private final String uniqCodeKey = AUTO_INCREMENT_KEY_SEND_IN_CODE.getKeyTemplate();
    private final String uniqCodePrefixKey = PREFIX_SEND_IN_CODE.getKeyTemplate();
    @Resource
    private UniqCodeGenerator uniqCodeGenerator;
    @PostConstruct
    public void SupplySendInServiceImpl(){
        SupplySendIn last = supplySendInRepository.findFirstByOrderByIdDesc();
        uniqCodeGenerator.setInitUniqUid(last!=null?last.getCode():"",uniqCodeKey,uniqCodePrefixKey, SEND_IN_CODE_DEFAULT_PREFIX);
    }


    public String generateCode() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        long count = supplySendInRepository.countByCodeStartsWith(String.format("%s%s", uniqCodeGenerator.getUniqCodePrefix(), dateStr));
        if (count == 0) {
            uniqCodeGenerator.setUniqUid(0L);
        }
        return String.format("%s%s%04d", uniqCodeGenerator.getUniqCodePrefix(), dateStr, uniqCodeGenerator.generateUniqUid());
    }

    @Resource
    private InventoryCheckInRepository inventoryCheckInRepository;

    @Resource
    private SupplySendInRepository supplySendInRepository;

    @Resource
    private SupplySendInMapper supplySendInMapper;

    @Resource
    private SupplySendInItemRepository supplySendInItemRepository;

    @Resource
    private SupplySendInItemMapper supplySendInItemMapper;

    @Resource
    private InventoryStoreInRepository inventoryStoreInRepository;

    @Override
    public Long createSupplySendIn(SupplySendInCreateReqVO createReqVO) {
        // 插入
        SupplySendIn supplySendIn = supplySendInMapper.toEntity(createReqVO);
        supplySendInRepository.save(supplySendIn);
        // 返回
        return supplySendIn.getId();
    }

    @Override
    public void updateSupplySendIn(SupplySendInUpdateReqVO updateReqVO) {
        // 校验存在
        validateSupplySendInExists(updateReqVO.getId());
        // 更新
        SupplySendIn updateObj = supplySendInMapper.toEntity(updateReqVO);
        supplySendInRepository.save(updateObj);
    }

    /**
     * 全量更新
     *
     * @param saveReqVO
     */
    @Override
    public void saveSupplySendIn(SupplySendInSaveReqVO saveReqVO) {
        if (saveReqVO.getId() != null) {
            // 存在 id，更新操作
            Long id = saveReqVO.getId();
            // 校验存在
            validateSupplySendInExists(id);
        }else{
            saveReqVO.setCode(generateCode());
        }

        // 更新或新建
        SupplySendIn updateObj = supplySendInMapper.toEntity(saveReqVO);
        updateObj.setWaitCheckIn(true); // 代签收
//        updateObj.setCode(String.valueOf(Instant.now().toEpochMilli())); //TODO 生成单号
        updateObj = supplySendInRepository.save(updateObj);
        Long sendInId = updateObj.getId();

        // 删除原有的
        supplySendInItemRepository.deleteBySupplySendInId(sendInId);

        // 更新 items
        supplySendInItemRepository.saveAll(saveReqVO.getItems().stream().map(item -> {
            item.setSupplySendInId(sendInId);
            return supplySendInItemMapper.toEntity(item);
        }).collect(Collectors.toList()));

    }

    @Override
    public void deleteSupplySendIn(Long id) {
        // 校验存在
        validateSupplySendInExists(id);

        supplySendInItemRepository.deleteBySupplySendInId(id);

        // 删除
        supplySendInRepository.deleteById(id);
    }

    private void validateSupplySendInExists(Long id) {
        supplySendInRepository.findById(id).orElseThrow(() -> exception(SUPPLY_SEND_IN_NOT_EXISTS));
    }

    @Override
    public Optional<SupplySendIn> getSupplySendIn(Long id) {
        return supplySendInRepository.findById(id);
    }

    @Override
    public List<SupplySendIn> getSupplySendInList(Collection<Long> ids) {
        return StreamSupport.stream(supplySendInRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<SupplySendIn> getSupplySendInPage(SupplySendInPageReqVO pageReqVO, SupplySendInPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<SupplySendIn> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(pageReqVO.getProductCode()!=null){
                List<SupplySendInItem> byProductCode = supplySendInItemRepository.findByProductCodeStartsWith(pageReqVO.getProductCode());
                //根据List<ProcurementItem>获取List<Long>并去重
                List<Long> ids = byProductCode.stream().map(SupplySendInItem::getSupplySendInId).distinct().collect(Collectors.toList());
                if(!ids.isEmpty()) {
                    predicates.add(root.get("id").in(ids));
                }
            }

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getProjectCategoryId() != null) {
                predicates.add(cb.equal(root.get("projectCategoryId"), pageReqVO.getProjectCategoryId()));
            }

            if (pageReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + pageReqVO.getCode() + "%"));
            }

            if (pageReqVO.getShipmentNumber() != null) {
                predicates.add(cb.equal(root.get("shipmentNumber"), pageReqVO.getShipmentNumber()));
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
            if (pageReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), pageReqVO.getAddress()));
            }

            if (pageReqVO.getReceiverName() != null) {
                predicates.add(cb.like(root.get("receiverName"), "%" + pageReqVO.getReceiverName() + "%"));
            }

            if (pageReqVO.getReceiverPhone() != null) {
                predicates.add(cb.equal(root.get("receiverPhone"), pageReqVO.getReceiverPhone()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        Page<SupplySendIn> page = supplySendInRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public List<SupplySendIn> getSupplySendInList(SupplySendInExportReqVO exportReqVO) {
        // 创建 Specification
        Specification<SupplySendIn> spec = (root, query, cb) -> {
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

            if (exportReqVO.getShipmentNumber() != null) {
                predicates.add(cb.equal(root.get("shipmentNumber"), exportReqVO.getShipmentNumber()));
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
            if (exportReqVO.getAddress() != null) {
                predicates.add(cb.equal(root.get("address"), exportReqVO.getAddress()));
            }

            if (exportReqVO.getReceiverName() != null) {
                predicates.add(cb.like(root.get("receiverName"), "%" + exportReqVO.getReceiverName() + "%"));
            }

            if (exportReqVO.getReceiverPhone() != null) {
                predicates.add(cb.equal(root.get("receiverPhone"), exportReqVO.getReceiverPhone()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 执行查询
        return supplySendInRepository.findAll(spec);
    }

    /**
     * @param saveReqVO
     */
    @Override
    public void checkIn(SendInCheckInReqVO saveReqVO) {
        // 校验存在
        validateSupplySendInExists(saveReqVO.getSendInId());

        // 根据 saveReqVo 的 list ，遍历每一项
        if (saveReqVO.getList() != null && saveReqVO.getList().size() > 0) {
            AtomicBoolean allCheckIn = new AtomicBoolean(true);

            saveReqVO.getList().forEach(checkIn -> {
                Long projectSupplyId = checkIn.getProjectSupplyId();
                String status = checkIn.getStatus();
                Integer checkInQuantity = checkIn.getCheckInNum();
                SupplySendInItem item = supplySendInItemRepository.findBySupplySendInIdAndProjectSupplyId(saveReqVO.getSendInId(), projectSupplyId);
                if (item != null) {
                    checkInQuantity += item.getCheckInQuantity();

                    if (checkInQuantity < item.getQuantity()) {
                        // 还有需要签收的子项
                        allCheckIn.set(false);
                    }

                    item.setCheckInQuantity(checkInQuantity);
                    item.setStatus(status);
                    supplySendInItemRepository.save(item);

                    // 保存签收日志
                    InventoryCheckIn checkInLog = new InventoryCheckIn();
                    checkInLog.setProjectSupplyId(item.getProjectSupplyId());
                    checkInLog.setInQuantity(checkIn.getCheckInNum());
                    checkInLog.setType(InventoryCheckInTypeEnums.PROCUREMENT.toString());
                    checkInLog.setMark(checkIn.getMark());
                    checkInLog.setStatus(checkIn.getStatus());
                    checkInLog.setRefId(saveReqVO.getSendInId());
                    checkInLog.setRefItemId(item.getId());
                    inventoryCheckInRepository.save(checkInLog);
                }
            });

            supplySendInRepository.findById(saveReqVO.getSendInId()).ifPresent(supplySendIn -> {
                supplySendIn.setWaitCheckIn(!allCheckIn.get());
                supplySendIn.setWaitStoreIn(true);
                supplySendInRepository.save(supplySendIn);
            });
        }
    }

    /**
     * @param saveReqVO
     */
    @Override
    @Transactional
    public void storeIn(StoreInSendInItemReqVO saveReqVO) {
        // 校验存在
        validateSupplySendInExists(saveReqVO.getSendInId());

        // 更新采购单项的状态
        // 根据 saveReqVo 的 list，遍历每一项，去更新采购单项的状态
        if (saveReqVO.getList() != null && saveReqVO.getList().size() > 0) {
            AtomicBoolean allStoreIn = new AtomicBoolean(true);

            saveReqVO.getList().forEach(storeIn -> {
                Long projectSupplyId = storeIn.getProjectSupplyId();
                String status = storeIn.getStatus();
                Integer storeInQuantity = storeIn.getInNum();
                SupplySendInItem item = supplySendInItemRepository.findBySupplySendInIdAndProjectSupplyId(saveReqVO.getSendInId(), projectSupplyId);
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

                    supplySendInItemRepository.save(item);

                    // 保存入库日志
                    InventoryStoreIn storeInLog = new InventoryStoreIn();
                    storeInLog.setProjectSupplyId(item.getProjectSupplyId());
                    storeInLog.setInQuantity(storeIn.getInNum());
                    storeInLog.setType(InventoryStoreInTypeEnums.PROCUREMENT.toString());
                    storeInLog.setMark(item.getMark());
                    storeInLog.setStatus(storeIn.getStatus());
                    storeInLog.setRefId(saveReqVO.getSendInId());
                    storeInLog.setRefItemId(item.getId());
                    storeInLog.setRoomId(storeIn.getRoomId());
                    storeInLog.setContainerId(storeIn.getContainerId());
                    storeInLog.setPlaceId(storeIn.getPlaceId());
                    storeInLog.setTemperature(storeIn.getTemperature());
                    storeInLog.setValidDate(storeIn.getValidDate());
                    storeInLog.setLocationName(storeIn.getLocationName());
                    inventoryStoreInRepository.save(storeInLog);

                }

            });

            // 更新采购单的待入库状态
            supplySendInRepository.updateWaitStoreInById(saveReqVO.getSendInId(), !allStoreIn.get());
        }

    }

    private Sort createSort(SupplySendInPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

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

        if (order.getShipmentNumber() != null) {
            orders.add(new Sort.Order(order.getShipmentNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "shipmentNumber"));
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

        if (order.getAddress() != null) {
            orders.add(new Sort.Order(order.getAddress().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "address"));
        }

        if (order.getReceiverName() != null) {
            orders.add(new Sort.Order(order.getReceiverName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiverName"));
        }

        if (order.getReceiverPhone() != null) {
            orders.add(new Sort.Order(order.getReceiverPhone().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "receiverPhone"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
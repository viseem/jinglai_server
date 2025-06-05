package cn.iocoder.yudao.module.jl.service.animal;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrderOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.AnimalFeedBillRulesEnums;
import cn.iocoder.yudao.module.jl.enums.AnimalFeedStageEnums;
import cn.iocoder.yudao.module.jl.mapper.animal.AnimalFeedOrderMapper;
import cn.iocoder.yudao.module.jl.repository.animal.*;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerSimpleRepository;
import cn.iocoder.yudao.module.jl.service.dept.XDeptServiceImpl;
import cn.iocoder.yudao.module.jl.service.user.UserServiceImpl;
import cn.iocoder.yudao.module.jl.utils.UniqCodeGenerator;
import cn.iocoder.yudao.module.system.api.dict.DictDataApiImpl;
import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.ANIMAL_FEED_ORDER_NOT_EXISTS;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.*;

/**
 * 动物饲养申请单 Service 实现类
 */
@Service
@Validated
public class AnimalFeedOrderServiceImpl implements AnimalFeedOrderService {

    private final String uniqCodeKey = AUTO_INCREMENT_KEY_ANIMAL_FEED_ORDER.getKeyTemplate();
    private final String uniqCodePrefixKey = PREFIX_ANIMAL_FEED_ORDER.getKeyTemplate();

    /**
     * OA 对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "ANIMAL_FEED_AUDIT";
    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Resource
    private UniqCodeGenerator uniqCodeGenerator;

    @Resource
    private DictDataApiImpl dictDataApi;

    @PostConstruct
    public void ProcurementServiceImpl() {
        AnimalFeedOrder last = animalFeedOrderRepository.findFirstByOrderByIdDesc();
        uniqCodeGenerator.setInitUniqUid(last != null ? last.getCode() : "", uniqCodeKey);
    }

    public String generateCode() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        long count = animalFeedOrderRepository.countByCodeStartsWith(ANIMAL_FEED_ORDER_DEFAULT_PREFIX + dateStr);
        if (count == 0) {
            uniqCodeGenerator.setUniqUid(0L);
        }
        return String.format("%s%s%04d", ANIMAL_FEED_ORDER_DEFAULT_PREFIX, dateStr, uniqCodeGenerator.generateUniqUid());
    }

    @Resource
    private AnimalFeedOrderRepository animalFeedOrderRepository;

    @Resource
    private AnimalFeedOrderOnlyRepository animalFeedOrderOnlyRepository;

    @Resource
    private AnimalBoxRepository animalBoxRepository;

    @Resource
    private AnimalFeedCardRepository animalFeedCardRepository;

    @Resource
    private AnimalFeedStoreInRepository animalFeedStoreInRepository;

    @Resource
    private AnimalFeedOrderMapper animalFeedOrderMapper;

    @Resource
    private CustomerSimpleRepository customerSimpleRepository;

    @Resource
    private XDeptServiceImpl deptService;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Resource
    private UserServiceImpl userService;

    @Override
    public Long createAnimalFeedOrder(AnimalFeedOrderCreateReqVO createReqVO) {
        // 插入
        createReqVO.setCode(generateCode());
        AnimalFeedOrder animalFeedOrder = animalFeedOrderMapper.toEntity(createReqVO);
        animalFeedOrderRepository.save(animalFeedOrder);
        // 返回
        return animalFeedOrder.getId();
    }

    @Override
    public void updateAnimalFeedOrder(AnimalFeedOrderUpdateReqVO updateReqVO) {
        // 校验存在
        validateAnimalFeedOrderExists(updateReqVO.getId());
        // 更新
        AnimalFeedOrder updateObj = animalFeedOrderMapper.toEntity(updateReqVO);
        animalFeedOrderRepository.save(updateObj);
    }

    @Override
    public void updateAnimalFeedOrderStatus(AnimalFeedOrderNoRequireBaseVO updateVO) {

        // 校验存在
        validateAnimalFeedOrderExists(updateVO.getId());
        if (updateVO.getStage() == null) {
            return;
        }
        animalFeedOrderRepository.updateStageById(updateVO.getStage(), updateVO.getId());
    }

    @Override
    @Transactional
    public void saveAnimalFeedOrder(AnimalFeedOrderSaveReqVO saveReqVO) {
        // 校验存在
        // 更新
        if (saveReqVO.getId() == null || saveReqVO.getId() <= 0) {
            saveReqVO.setCode(generateCode());
        }
        AnimalFeedOrder saveObj = animalFeedOrderMapper.toEntity(saveReqVO);
        saveObj.setStage(AnimalFeedStageEnums.APPROVAL_SUCCESS.getStatus().toString());
        AnimalFeedOrder animalFeedOrder = animalFeedOrderRepository.save(saveObj);
        Long id = animalFeedOrder.getId();

        if (saveReqVO.getCards() != null) {
            animalFeedCardRepository.saveAll(saveReqVO.getCards().stream().map(item -> {
                item.setFeedOrderId(id);
                item.setProjectId(animalFeedOrder.getProjectId());
                item.setCustomerId(animalFeedOrder.getCustomerId());
                item.setBreed(saveObj.getBreed());
                return item;
            }).collect(Collectors.toList()));
        }

        // 发起 BPM 流程
        if (saveReqVO.getNeedAudit()) {
            Map<String, Object> processInstanceVariables = new HashMap<>();
            String processInstanceId = processInstanceApi.createProcessInstance(getLoginUserId(),
                    new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                            .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(saveObj.getId())));
            // 更新流程实例编号
            animalFeedOrderRepository.updateProcessInstanceIdById(processInstanceId, saveObj.getId());
        }

        // 通知饲养部负责人
        Long animalFeedDeptManagerId = deptService.getAnimalFeedDeptManagerId();
        if(animalFeedDeptManagerId!=null){
            User user = userService.validateUserExists(getLoginUserId());
            Map<String, Object> templateParams = new HashMap<>();
            String content = String.format(
                    "收到(%s)提交的饲养申请单(%s)，请及时处理",
                    user.getNickname(),
                    saveReqVO.getName()
            );
            templateParams.put("id", saveObj.getId());
            templateParams.put("content", content);
            notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                    animalFeedDeptManagerId,
                    BpmMessageEnum.NOTIFY_WHEN_ANIMAL_FEED_APPLY.getTemplateCode(), templateParams
            ));
        }


    }

    @Override
    @Transactional
    public void storeAnimalFeedOrder(AnimalFeedOrderStoreReqVO storeReqVO) {

        // 查询一下客户，把客户的销售id设置到storeReqVO上
        customerSimpleRepository.findById(storeReqVO.getCustomerId()).ifPresentOrElse(customer->{
            storeReqVO.setSalesId(customer.getSalesId());
        },()->{
            throw exception(CUSTOMER_NOT_EXISTS);
        });


        // 校验存在
        storeReqVO.setStage(AnimalFeedStageEnums.FEEDING.getStatus());
        AnimalFeedOrder saveObj = animalFeedOrderMapper.toEntity(storeReqVO);
        if (saveObj.getCode() == null) {
            saveObj.setCode(generateCode());
        }
        AnimalFeedOrder animalFeedOrder = animalFeedOrderRepository.save(saveObj);
        Long id = animalFeedOrder.getId();

        //更新鼠牌
        animalFeedCardRepository.saveAll(storeReqVO.getCards().stream().map(item -> {
            item.setFeedOrderId(id);
            item.setProjectId(animalFeedOrder.getProjectId());
            item.setCustomerId(animalFeedOrder.getCustomerId());
            item.setBreed(saveObj.getBreed());
            return item;
        }).collect(Collectors.toList()));

        //更新入库信息
        animalFeedStoreInRepository.saveAll(storeReqVO.getStores().stream().map(item -> {
            item.setFeedOrderId(id);
            return item;
        }).collect(Collectors.toList()));

        //更新笼位信息
        animalBoxRepository.saveAll(storeReqVO.getBoxes().stream().peek(item -> {
            item.setFeedOrderId(animalFeedOrder.getId());
            item.setFeedOrderName(animalFeedOrder.getName());
            item.setFeedOrderCode(animalFeedOrder.getCode());
            if (animalFeedOrder.getProject() != null) {
                item.setProjectName(animalFeedOrder.getProject().getName());
            }
            if (animalFeedOrder.getCustomer() != null) {
                item.setCustomerName(animalFeedOrder.getCustomer().getName());
            }
        }).collect(Collectors.toList()));

    }

    @Override
    public void deleteAnimalFeedOrder(Long id) {
        // 校验存在
        validateAnimalFeedOrderExists(id);
        // 删除
        animalFeedOrderRepository.deleteById(id);
    }

    private void validateAnimalFeedOrderExists(Long id) {
        animalFeedOrderRepository.findById(id).orElseThrow(() -> exception(ANIMAL_FEED_ORDER_NOT_EXISTS));
    }

    @Override
    public Optional<AnimalFeedOrder> getAnimalFeedOrder(Long id) {
        Optional<AnimalFeedOrder> byId = animalFeedOrderRepository.findById(id);


        if (byId.isPresent()) {
            AnimalFeedOrder animalFeedOrder = byId.get();
            animalFeedOrder.setAmount(processFeedOrderAmount(animalFeedOrder,animalFeedOrder.getStartDate(),animalFeedOrder.getEndDate()));
        }
        return byId;
    }

    private BigDecimal processFeedOrderAmount(AnimalFeedOrder order, LocalDateTime start, LocalDateTime end, int... needSetCurrentEnd) {
        // 1. 处理时间范围
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = (end == null || end.isAfter(now)) ? now : end;
        if (needSetCurrentEnd.length > 0) {
            order.setCurrentEndDate(endDate);
        }
        if (order.getUnitFee() == null || start == null || start.isAfter(endDate)) {
            order.setCurrentQuantity(0);
            order.setCurrentCageQuantity(0);
            return BigDecimal.ZERO;
        }

        // 2. 计费模式
        boolean isPerAnimal = AnimalFeedBillRulesEnums.ONE.getStatus().equals(order.getBillRules());
        int baseQuantity = isPerAnimal ? (order.getQuantity() != null ? order.getQuantity() : 0) : (order.getCageQuantity() != null ? order.getCageQuantity() : 0);
        if (baseQuantity == 0) {
            order.setCurrentQuantity(0);
            order.setCurrentCageQuantity(0);
            return BigDecimal.ZERO;
        }

        // 3. 日志排序
        List<AnimalFeedLog> logs = Optional.ofNullable(order.getLogs())
                .orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(AnimalFeedLog::getOperateTime))
                .collect(Collectors.toList());

        // 4. 计算起始数量（初始化为订单原始数量）
        int initialQuantity = order.getQuantity() != null ? order.getQuantity() : 0;
        int initialCageQuantity = order.getCageQuantity() != null ? order.getCageQuantity() : 0;
        
        // 当前数量（会随着日志变化）
        int currentQuantity = initialQuantity;
        int currentCageQuantity = initialCageQuantity;
        
        // 应用开始时间前的所有日志变化
        for (AnimalFeedLog log : logs) {
            if (log.getOperateTime().isBefore(start)) {
                currentQuantity += log.getChangeQuantity() != null ? log.getChangeQuantity() : 0;
                currentCageQuantity += log.getChangeCageQuantity() != null ? log.getChangeCageQuantity() : 0;
            }
        }
        
        // 记录开始计费时的数量
        int startQuantity = currentQuantity;
        int startCageQuantity = currentCageQuantity;

        // 5. 遍历每一天，遇到日志变化，次日生效
        Map<LocalDate, Integer> dateToQuantity = new LinkedHashMap<>();
        LocalDate date = start.toLocalDate();
        LocalDate endDateLocal = endDate.toLocalDate();
        int quantity = startQuantity;
        int cageQuantity = startCageQuantity;
        int logIndex = 0;
        int nextDayChange = 0;
        int nextDayCageChange = 0;
        
        // 按日期遍历每一天
        while (!date.isAfter(endDateLocal)) {
            // 处理当天所有日志，变化次日生效
            while (logIndex < logs.size() && logs.get(logIndex).getOperateTime().toLocalDate().equals(date)) {
                nextDayChange += logs.get(logIndex).getChangeQuantity() != null ? logs.get(logIndex).getChangeQuantity() : 0;
                nextDayCageChange += logs.get(logIndex).getChangeCageQuantity() != null ? logs.get(logIndex).getChangeCageQuantity() : 0;
                formatLogDisplayInfo(logs.get(logIndex));
                logIndex++;
            }
            
            // 记录当天数量
            dateToQuantity.put(date, isPerAnimal ? quantity : cageQuantity);
            
            // 应用前一天的变化
            quantity += nextDayChange;
            cageQuantity += nextDayCageChange;
            nextDayChange = 0;
            nextDayCageChange = 0;
            date = date.plusDays(1);
        }

        // 6. 继续处理剩余日志以获取最终的当前数量
        while (logIndex < logs.size()) {
            AnimalFeedLog log = logs.get(logIndex);
            quantity += log.getChangeQuantity() != null ? log.getChangeQuantity() : 0;
            cageQuantity += log.getChangeCageQuantity() != null ? log.getChangeCageQuantity() : 0;
            formatLogDisplayInfo(log);
            logIndex++;
        }

        // 7. 设置当前数量（最终状态）
        order.setCurrentQuantity(quantity);
        order.setCurrentCageQuantity(cageQuantity);

        // 8. 计算总数量（每天的数量之和）
        long totalQuantity = dateToQuantity.values().stream()
                .mapToLong(Integer::longValue)
                .sum();

        // 9. 乘以单价
        return order.getUnitFee().multiply(BigDecimal.valueOf(totalQuantity));
    }

    // 辅助方法：格式化日志显示信息
    private void formatLogDisplayInfo(AnimalFeedLog log) {
        log.setDateStr(log.getOperateTime().format(DateTimeFormatter.ISO_LOCAL_DATE));
        log.setTimeStr(log.getOperateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

/*    private void processLatestFeedStore(AnimalFeedOrder animalFeedOrder) {
        List<AnimalFeedStoreIn> stores = animalFeedOrder.getStores();
        if (stores != null && stores.size() > 0) {
            animalFeedOrder.setLatestStore(stores.get(0));
        }
    }*/

    @Override
    public List<AnimalFeedOrder> getAnimalFeedOrderList(Collection<Long> ids) {
        return StreamSupport.stream(animalFeedOrderRepository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalFeedOrderStatsCountRespVO getAnimalFeedOrderStatsCount() {
        //获得等待饲养的饲养单数量
        Long waitingCount = animalFeedOrderRepository.countByStage(AnimalFeedStageEnums.APPROVAL_SUCCESS.getStatus());
        //获得饲养中的饲养单数量
        Long feedingCount = animalFeedOrderRepository.countByStage(AnimalFeedStageEnums.FEEDING.getStatus());
        //获得已完成的饲养单数量
        Long finishedCount = animalFeedOrderRepository.countByStage(AnimalFeedStageEnums.END.getStatus());
        //回写数据
        AnimalFeedOrderStatsCountRespVO respVO = new AnimalFeedOrderStatsCountRespVO();
        respVO.setWaitingFeedCount(waitingCount);
        respVO.setFeedingCount(feedingCount);
        respVO.setEndCount(finishedCount);
        return respVO;
    }

    @Override
    public PageResult<AnimalFeedOrder> getAnimalFeedOrderPage(AnimalFeedOrderPageReqVO pageReqVO, AnimalFeedOrderPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalFeedOrder> spec = getAnimalFeedOrderSpecification(pageReqVO);

        // 执行查询
        Page<AnimalFeedOrder> page = animalFeedOrderRepository.findAll(spec, pageable);

        List<AnimalFeedOrder> animalFeedOrders = page.getContent();
        animalFeedOrders.forEach(animalFeedOrder -> {
            animalFeedOrder.setCurrentStartDate(getMaxDateTime(pageReqVO.getStartDate(),animalFeedOrder.getStartDate()));
            animalFeedOrder.setCurrentEndDate(getMinDateTime(pageReqVO.getEndDate(),animalFeedOrder.getEndDate()));
            animalFeedOrder.setCurrentAmount(processFeedOrderAmount(animalFeedOrder,animalFeedOrder.getCurrentStartDate(),animalFeedOrder.getCurrentEndDate(),1));
            animalFeedOrder.setAmount(processFeedOrderAmount(animalFeedOrder,animalFeedOrder.getStartDate(),animalFeedOrder.getEndDate()));

        });

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public PageResult<AnimalFeedOrderOnly> getAnimalFeedOrderPageOnly(AnimalFeedOrderPageReqVO pageReqVO, AnimalFeedOrderPageOrder orderV0) {
        // 创建 Sort 对象
        Sort sort = createSort(orderV0);

        // 创建 Pageable 对象
        Pageable pageable = PageRequest.of(pageReqVO.getPageNo() - 1, pageReqVO.getPageSize(), sort);

        // 创建 Specification
        Specification<AnimalFeedOrderOnly> spec = getAnimalFeedOrderSpecification(pageReqVO);

        // 执行查询
        Page<AnimalFeedOrderOnly> page = animalFeedOrderOnlyRepository.findAll(spec, pageable);

        // 转换为 PageResult 并返回
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    private static <T>Specification<T> getAnimalFeedOrderSpecification(AnimalFeedOrderPageReqVO pageReqVO) {
        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pageReqVO.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + pageReqVO.getName() + "%"));
            }

            if (pageReqVO.getCode() != null) {
                predicates.add(cb.like(root.get("code"), "%" + pageReqVO.getCode() + "%"));
            }
/*            if(pageReqVO.getBetweenDate() != null) {
                predicates.add(cb.between(root.get("startDate"), pageReqVO.getBetweenDate()[0], pageReqVO.getBetweenDate()[1]));
                predicates.add(cb.between(root.get("endDate"), pageReqVO.getBetweenDate()[0], pageReqVO.getBetweenDate()[1]));
            }*/

            if(pageReqVO.getStartDate() != null){
                //
//                predicates.add(cb.or(cb.greaterThanOrEqualTo(root.get("endDate"),pageReqVO.getStartDate()),cb.isNull(root.get("endDate"))));
                predicates.add(cb.or(cb.and(cb.lessThanOrEqualTo(root.get("startDate"),pageReqVO.getStartDate()),cb.greaterThanOrEqualTo(root.get("endDate"),pageReqVO.getStartDate())),
                        cb.and(cb.lessThanOrEqualTo(root.get("startDate"),pageReqVO.getEndDate()),cb.greaterThanOrEqualTo(root.get("endDate"),pageReqVO.getEndDate()))
                        ));
/*                predicates.add(cb.and(cb.lessThanOrEqualTo(root.get("startDate"),pageReqVO.getStartDate()),cb.greaterThanOrEqualTo(root.get("endDate"),pageReqVO.getStartDate())));

                if(pageReqVO.getEndDate()!=null){
                    predicates.add(cb.and(cb.lessThanOrEqualTo(root.get("startDate"),pageReqVO.getEndDate()),cb.greaterThanOrEqualTo(root.get("endDate"),pageReqVO.getEndDate())));
                }*/
            }


/*            if(pageReqVO.getStartDate() != null&&pageReqVO.getEndDate()!=null){
                predicates.add(cb.or(cb.between(root.get("endDate"), pageReqVO.getStartDate(), pageReqVO.getEndDate()),cb.between(root.get("startDate"), pageReqVO.getStartDate(), pageReqVO.getEndDate())));
            }*/

/*            if(pageReqVO.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"),pageReqVO.getStartDate()));
            }

            if(pageReqVO.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"),pageReqVO.getEndDate()));
            }*/

            if (pageReqVO.getSalesId() != null) {
                predicates.add(cb.equal(root.get("salesId"), pageReqVO.getSalesId()));
            }

            if (pageReqVO.getBreed() != null) {
                predicates.add(cb.equal(root.get("breed"), pageReqVO.getBreed()));
            }

            if (pageReqVO.getBreedCate() != null) {
                predicates.add(cb.equal(root.get("breedCate"), pageReqVO.getBreedCate()));
            }

            if (pageReqVO.getStrainCate() != null) {
                predicates.add(cb.equal(root.get("strainCate"), pageReqVO.getBreed()));
            }

            if (pageReqVO.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), pageReqVO.getAge()));
            }

            if (pageReqVO.getQuantity() != null) {
                predicates.add(cb.equal(root.get("quantity"), pageReqVO.getQuantity()));
            }

            if (pageReqVO.getFemaleCount() != null) {
                predicates.add(cb.equal(root.get("femaleCount"), pageReqVO.getFemaleCount()));
            }

            if (pageReqVO.getMaleCount() != null) {
                predicates.add(cb.equal(root.get("maleCount"), pageReqVO.getMaleCount()));
            }

            if (pageReqVO.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplierId"), pageReqVO.getSupplierId()));
            }

            if (pageReqVO.getSupplierName() != null) {
                predicates.add(cb.like(root.get("supplierName"), "%" + pageReqVO.getSupplierName() + "%"));
            }

            if (pageReqVO.getCertificateNumber() != null) {
                predicates.add(cb.like(root.get("certificateNumber"), "%" + pageReqVO.getCertificateNumber() + "%"));
            }

            if (pageReqVO.getLicenseNumber() != null) {
                predicates.add(cb.like(root.get("licenseNumber"), "%" + pageReqVO.getLicenseNumber() + "%"));
            }
            if (pageReqVO.getHasDanger() != null) {
                predicates.add(cb.equal(root.get("hasDanger"), pageReqVO.getHasDanger()));
            }

            if (pageReqVO.getFeedType() != null) {
                predicates.add(cb.equal(root.get("feedType"), pageReqVO.getFeedType()));
            }

            if (pageReqVO.getMark() != null) {
                predicates.add(cb.equal(root.get("mark"), pageReqVO.getMark()));
            }

            if (pageReqVO.getProjectId() != null) {
                predicates.add(cb.equal(root.get("projectId"), pageReqVO.getProjectId()));
            }

            if (pageReqVO.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customerId"), pageReqVO.getCustomerId()));
            }

            if (pageReqVO.getStage() != null) {
                predicates.add(cb.equal(root.get("stage"), pageReqVO.getStage()));
            }

            if (pageReqVO.getReply() != null) {
                predicates.add(cb.equal(root.get("reply"), pageReqVO.getReply()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return spec;
    }

    public static LocalDateTime getMinDateTime(LocalDateTime dt1, LocalDateTime dt2) {
        if (dt1 == null) {
            return dt2;
        } else if (dt2 == null) {
            return dt1;
        } else {
            return dt1.isBefore(dt2) ? dt1 : dt2;
        }
    }

    public static LocalDateTime getMaxDateTime(LocalDateTime dt1, LocalDateTime dt2) {
        if (dt1 == null) {
            return dt2;
        } else if (dt2 == null) {
            return dt1;
        } else {
            return dt1.isAfter(dt2) ? dt1 : dt2;
        }
    }

    @Override
    public List<AnimalFeedOrder> getAnimalFeedOrderList(AnimalFeedOrderPageReqVO exportReqVO) {


        // 创建 Specification
        Specification<AnimalFeedOrder> spec = getAnimalFeedOrderSpecification(exportReqVO);
        List<AnimalFeedOrder> animalFeedOrders = animalFeedOrderRepository.findAll(spec);

        List<DictDataRespDTO> billRules = dictDataApi.getDictDataByType(DictTypeConstants.FEED_BILL_RULES);

        animalFeedOrders.forEach(animalFeedOrder -> {
            for (DictDataRespDTO rule : billRules) {
                if (rule.getValue().equals(animalFeedOrder.getBillRules())) {
                    animalFeedOrder.setBillRulesLabel(rule.getLabel());
                }
            }

            animalFeedOrder.setCurrentStartDate(getMaxDateTime(exportReqVO.getStartDate(),animalFeedOrder.getStartDate()));
            animalFeedOrder.setCurrentEndDate(getMinDateTime(exportReqVO.getEndDate(),animalFeedOrder.getEndDate()));
            animalFeedOrder.setCurrentAmount(processFeedOrderAmount(animalFeedOrder,animalFeedOrder.getCurrentStartDate(),animalFeedOrder.getCurrentEndDate(),1));
            animalFeedOrder.setAmount(processFeedOrderAmount(animalFeedOrder,animalFeedOrder.getStartDate(),animalFeedOrder.getEndDate()));

        });

        // 执行查询
        return animalFeedOrderRepository.findAll(spec);
    }

    private Sort createSort(AnimalFeedOrderPageOrder order) {
        List<Sort.Order> orders = new ArrayList<>();

        // 根据 order 中的每个属性创建一个排序规则
        // 注意，这里假设 order 中的每个属性都是 String 类型，代表排序的方向（"asc" 或 "desc"）
        // 如果实际情况不同，你可能需要对这部分代码进行调整

        orders.add(new Sort.Order("asc".equals(order.getCreateTime()) ? Sort.Direction.ASC : Sort.Direction.DESC, "createTime"));

        if (order.getId() != null) {
            orders.add(new Sort.Order(order.getId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "id"));
        }

        if (order.getName() != null) {
            orders.add(new Sort.Order(order.getName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        if (order.getCode() != null) {
            orders.add(new Sort.Order(order.getCode().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "code"));
        }

        if (order.getBreed() != null) {
            orders.add(new Sort.Order(order.getBreed().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "breed"));
        }

        if (order.getAge() != null) {
            orders.add(new Sort.Order(order.getAge().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "age"));
        }

        if (order.getQuantity() != null) {
            orders.add(new Sort.Order(order.getQuantity().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "quantity"));
        }

        if (order.getFemaleCount() != null) {
            orders.add(new Sort.Order(order.getFemaleCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "femaleCount"));
        }

        if (order.getMaleCount() != null) {
            orders.add(new Sort.Order(order.getMaleCount().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "maleCount"));
        }

        if (order.getSupplierId() != null) {
            orders.add(new Sort.Order(order.getSupplierId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierId"));
        }

        if (order.getSupplierName() != null) {
            orders.add(new Sort.Order(order.getSupplierName().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "supplierName"));
        }

        if (order.getCertificateNumber() != null) {
            orders.add(new Sort.Order(order.getCertificateNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "certificateNumber"));
        }

        if (order.getLicenseNumber() != null) {
            orders.add(new Sort.Order(order.getLicenseNumber().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "licenseNumber"));
        }

        if (order.getHasDanger() != null) {
            orders.add(new Sort.Order(order.getHasDanger().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "hasDanger"));
        }

        if (order.getFeedType() != null) {
            orders.add(new Sort.Order(order.getFeedType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "feedType"));
        }

        if (order.getMark() != null) {
            orders.add(new Sort.Order(order.getMark().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "mark"));
        }

        if (order.getProjectId() != null) {
            orders.add(new Sort.Order(order.getProjectId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "projectId"));
        }

        if (order.getCustomerId() != null) {
            orders.add(new Sort.Order(order.getCustomerId().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "customerId"));
        }

        if (order.getStage() != null) {
            orders.add(new Sort.Order(order.getStage().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "stage"));
        }

        if (order.getReply() != null) {
            orders.add(new Sort.Order(order.getReply().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "reply"));
        }


        // 创建 Sort 对象
        return Sort.by(orders);
    }
}
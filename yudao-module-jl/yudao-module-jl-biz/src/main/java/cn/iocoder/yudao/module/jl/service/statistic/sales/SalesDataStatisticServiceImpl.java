package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticResp.SalesDataItem;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLogOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.statistic.SalesDataStatisticCache;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.enums.TimeRangeTypeEnum;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.statistic.SalesDataStatisticCacheRepository;
import cn.iocoder.yudao.module.jl.utils.TimeRangeUtil;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 销售数据统计 Service 实现类
 */
@Service
@Validated
@Slf4j
public class SalesDataStatisticServiceImpl implements SalesDataStatisticService {

    // 刷新状态管理（细粒度锁：按时间范围类型）
    private final ConcurrentHashMap<String, Boolean> refreshingRanges = new ConcurrentHashMap<>();
    
    // 允许缓存的时间范围类型
    private static final TimeRangeTypeEnum[] CACHEABLE_RANGES = {
        TimeRangeTypeEnum.TODAY,
        TimeRangeTypeEnum.YESTERDAY,
        TimeRangeTypeEnum.THIS_WEEK,
        TimeRangeTypeEnum.LAST_WEEK,
        TimeRangeTypeEnum.THIS_MONTH,
        TimeRangeTypeEnum.LAST_MONTH
    };

    @Resource
    private SalesDataStatisticCacheRepository salesDataStatisticCacheRepository;

    @Resource
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private ContractInvoiceLogOnlyRepository contractInvoiceLogOnlyRepository;

    @Resource
    private ContractFundLogOnlyRepository contractFundLogOnlyRepository;

    @Resource
    private AdminUserApi adminUserApi;

    /**
     * 获取销售数据统计（优先使用缓存，未命中时实时计算）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SalesDataItem> getSalesDataStatistic(SalesDataStatisticReqVO reqVO) {
        System.out.println("===== 开始查询销售数据统计 =====");
        System.out.println("请求参数 - userIds: " + 
            (reqVO.getUserIds() != null ? Arrays.toString(reqVO.getUserIds()) : "null") +
            ", startTime: " + reqVO.getStartTime() + ", endTime: " + reqVO.getEndTime());
        
        // 获取时间范围
        LocalDateTime startTime = reqVO.getStartTime();
        LocalDateTime endTime = reqVO.getEndTime();
        
        System.out.println("查询时间范围: " + startTime + " ~ " + endTime);
        
        // 识别时间范围类型
        TimeRangeTypeEnum rangeType = TimeRangeUtil.matchTimeRangeType(startTime, endTime);
        System.out.println("时间范围类型: " + rangeType.name());
        
        // 过滤掉无效的 userIds（0 或 null）
        List<Long> validUserIds = filterValidUserIds(reqVO.getUserIds());
        
        // 获取销售人员列表
        List<AdminUserRespDTO> salesUsers = getSalesUsers(validUserIds);
        if (salesUsers.isEmpty()) {
            System.out.println("未找到销售人员");
            return new ArrayList<>();
        }
        
        // 只有预定义范围才尝试从缓存获取（排除CUSTOM类型）
        if (rangeType != TimeRangeTypeEnum.CUSTOM && isCacheableRange(rangeType)) {
            List<SalesDataItem> cachedData = tryGetFromCacheByType(salesUsers, rangeType);
            if (!cachedData.isEmpty()) {
                System.out.println("从缓存获取到 " + cachedData.size() + " 条数据");
                return cachedData;
            }
            System.out.println("缓存未命中，开始实时计算");
        } else {
            System.out.println("自定义时间范围，不使用缓存，直接实时计算");
        }
        
        // 实时计算
        List<SalesDataItem> calculatedData = calculateSalesDataInRealTime(salesUsers, startTime, endTime);
        
        // 只缓存预定义范围的数据（同步保存，确保一致性）
        if (rangeType != TimeRangeTypeEnum.CUSTOM && isCacheableRange(rangeType) && !calculatedData.isEmpty()) {
            System.out.println("同步保存缓存数据...");
            updateCacheWithData(calculatedData, startTime, endTime);
        }
        
        System.out.println("返回 " + calculatedData.size() + " 条数据");
        System.out.println("===== 查询销售数据统计结束 =====");
        
        return calculatedData;
    }
    
    /**
     * 判断时间范围类型是否允许缓存
     */
    private boolean isCacheableRange(TimeRangeTypeEnum rangeType) {
        for (TimeRangeTypeEnum type : CACHEABLE_RANGES) {
            if (type == rangeType) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新销售数据统计缓存（定时任务调用）- 更新预定义时间范围的缓存
     */
    @Override
    public void updateSalesDataStatisticCache() {
        System.out.println("========== 开始更新销售数据统计缓存（定时任务） ==========");
        
        // 获取所有销售人员（sales + sale_manager）
        List<AdminUserRespDTO> salesUsers = getSalesUsers(new ArrayList<>());
        if (salesUsers.isEmpty()) {
            System.out.println("未找到销售人员，跳过更新");
            return;
        }
        
        System.out.println("获取到 " + salesUsers.size() + " 个销售人员");
        
        // 遍历所有允许缓存的时间范围类型
        for (TimeRangeTypeEnum type : CACHEABLE_RANGES) {
            try {
                updateCacheForTimeRangeType(salesUsers, type);
            } catch (Exception e) {
                System.err.println("更新" + getTimeRangeName(type) + "缓存失败: " + e.getMessage());
                e.printStackTrace();
                // 继续更新其他范围
            }
        }
        
        System.out.println("========== 销售数据统计缓存更新完成 ==========");
    }
    
    /**
     * 更新指定时间范围类型的缓存（使用细粒度锁）
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateCacheForTimeRangeType(List<AdminUserRespDTO> salesUsers, TimeRangeTypeEnum type) {
        String lockKey = type.name();
        
        // 尝试获取锁
        if (refreshingRanges.putIfAbsent(lockKey, true) != null) {
            System.out.println(getTimeRangeName(type) + " 正在刷新中，跳过");
            return;
        }
        
        try {
            System.out.println("正在计算" + getTimeRangeName(type) + "数据...");
            updateCacheForTimeRange(salesUsers, type);
            System.out.println(getTimeRangeName(type) + "数据更新完成");
        } finally {
            // 释放锁
            refreshingRanges.remove(lockKey);
        }
    }
    
    /**
     * 异步更新销售数据统计缓存（手动刷新调用）
     * 使用简单的内存标记 + 新线程方式，无需Spring异步配置
     */
    @Override
    public void updateSalesDataStatisticCacheAsync(SalesDataStatisticReqVO reqVO) {
        // 识别时间范围类型
        LocalDateTime startTime = reqVO.getStartTime();
        LocalDateTime endTime = reqVO.getEndTime();
        TimeRangeTypeEnum rangeType = TimeRangeUtil.matchTimeRangeType(startTime, endTime);
        
        if (rangeType == TimeRangeTypeEnum.CUSTOM || !isCacheableRange(rangeType)) {
            System.out.println("非预定义时间范围，不支持刷新缓存");
            return;
        }
        
        // 在新线程中执行刷新任务
        Thread refreshThread = new Thread(() -> {
            System.out.println("========== 开始异步更新销售数据统计缓存 ==========");
            System.out.println("刷新线程: " + Thread.currentThread().getName());
            System.out.println("刷新时间范围: " + startTime + " ~ " + endTime);
            System.out.println("时间范围类型: " + rangeType.name());
            
            // 获取所有销售人员（sales + sale_manager）
            List<AdminUserRespDTO> salesUsers = getSalesUsers(new ArrayList<>());
            if (salesUsers.isEmpty()) {
                System.out.println("未找到销售人员，跳过更新");
                return;
            }
            
            try {
                // 使用细粒度锁更新指定时间范围
                updateCacheForTimeRangeType(salesUsers, rangeType);
            } catch (Exception e) {
                System.err.println("异步更新销售数据统计缓存失败: " + e.getMessage());
                e.printStackTrace();
            }
            
            System.out.println("========== 异步更新销售数据统计缓存完成 ==========");
        }, "SalesDataRefresh-" + rangeType.name());
        
        // 启动线程
        refreshThread.start();
        System.out.println("刷新任务已提交到线程: " + refreshThread.getName());
    }
    
    /**
     * 获取时间范围的中文名称
     */
    private String getTimeRangeName(TimeRangeTypeEnum type) {
        switch (type) {
            case TODAY: return "今日";
            case YESTERDAY: return "昨日";
            case THIS_WEEK: return "本周";
            case LAST_WEEK: return "上周";
            case THIS_MONTH: return "本月";
            case LAST_MONTH: return "上月";
            default: return type.name();
        }
    }

    // ===== 辅助方法 =====
    
    /**
     * 过滤有效的用户ID
     */
    private List<Long> filterValidUserIds(Long[] userIds) {
        List<Long> validUserIds = new ArrayList<>();
        if (userIds != null && userIds.length > 0) {
            for (Long userId : userIds) {
                if (userId != null && userId > 0) {
                    validUserIds.add(userId);
                }
            }
        }
        System.out.println("有效的 userIds: " + validUserIds);
        return validUserIds;
    }
    
    /**
     * 获取销售人员列表（包含 sales 和 sale_manager 角色）
     */
    private List<AdminUserRespDTO> getSalesUsers(List<Long> validUserIds) {
        List<AdminUserRespDTO> salesUsers;
        if (validUserIds.isEmpty()) {
            // 未指定销售人员，查询所有拥有销售角色的人员（sales + sale_manager）
            List<AdminUserRespDTO> salesList = adminUserApi.getUserListByRoleCode("sales");
            List<AdminUserRespDTO> managerList = adminUserApi.getUserListByRoleCode("sale_manager");
            
            // 合并两个列表并去重（按userId）
            Map<Long, AdminUserRespDTO> userMap = new HashMap<>();
            if (salesList != null) {
                salesList.forEach(user -> userMap.put(user.getId(), user));
            }
            if (managerList != null) {
                managerList.forEach(user -> userMap.put(user.getId(), user));
            }
            
            salesUsers = new ArrayList<>(userMap.values());
            System.out.println("查询所有销售人员（sales + sale_manager），共 " + salesUsers.size() + " 人");
        } else {
            // 指定了销售人员，只查询这些人员
            salesUsers = adminUserApi.getUserList(validUserIds);
            System.out.println("查询指定销售人员，共 " + (salesUsers != null ? salesUsers.size() : 0) + " 人");
        }
        return salesUsers != null ? salesUsers : new ArrayList<>();
    }
    
    /**
     * 按时间范围类型从缓存获取数据
     */
    private List<SalesDataItem> tryGetFromCacheByType(List<AdminUserRespDTO> salesUsers, TimeRangeTypeEnum rangeType) {
        List<SalesDataItem> result = new ArrayList<>();
        
        // 获取当前时间范围类型对应的实际时间范围
        TimeRangeUtil.TimeRange timeRange = TimeRangeUtil.calculateTimeRange(rangeType);
        LocalDateTime startTime = timeRange.getStartTime();
        LocalDateTime endTime = timeRange.getEndTime();
        
        System.out.println("从缓存查询 " + rangeType.name() + " 的数据，时间范围: " + startTime + " ~ " + endTime);
        
        // 根据时间范围查找缓存（由于是预定义范围，时间范围应该是固定的）
        List<SalesDataStatisticCache> cacheList = salesDataStatisticCacheRepository
                .findByTimeRange(startTime, endTime);
        
        if (!cacheList.isEmpty()) {
            Map<Long, SalesDataStatisticCache> cacheMap = cacheList.stream()
                    .collect(Collectors.toMap(SalesDataStatisticCache::getUserId, cache -> cache));
            
            for (AdminUserRespDTO user : salesUsers) {
                SalesDataStatisticCache cache = cacheMap.get(user.getId());
                if (cache != null) {
                    SalesDataItem item = convertCacheToItem(cache);
                    result.add(item);
                }
            }
            System.out.println("缓存命中，获取到 " + result.size() + " 条数据");
        } else {
            System.out.println("缓存未命中");
        }
        
        return result;
    }
    
    /**
     * 实时计算销售数据
     */
    private List<SalesDataItem> calculateSalesDataInRealTime(List<AdminUserRespDTO> salesUsers, 
                                                            LocalDateTime startTime, 
                                                            LocalDateTime endTime) {
        List<SalesDataItem> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (AdminUserRespDTO user : salesUsers) {
            System.out.println("计算用户 " + user.getId() + " (" + user.getNickname() + 
                ") 在时间范围 [" + startTime + " ~ " + endTime + "] 的统计数据");
            
            SalesDataItem item = calculateSalesDataWithTimeRange(user.getId(), user.getNickname(), startTime, endTime);
            item.setUpdateTime(now);
            result.add(item);
            
            System.out.println("用户 " + user.getNickname() + " - 成交金额: " + item.getOrderAmount() + 
                ", 应收金额: " + item.getAccountsReceivable() + ", 已开票金额: " + item.getInvoiceAmount() + 
                ", 回款金额: " + item.getPaymentAmount());
        }
        
        return result;
    }
    
    /**
     * 更新缓存数据
     */
    private void updateCacheWithData(List<SalesDataItem> data, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        
        // 删除旧缓存
        salesDataStatisticCacheRepository.deleteByTimeRange(startTime, endTime);
        
        // 保存新缓存
        List<SalesDataStatisticCache> cacheList = new ArrayList<>();
        for (SalesDataItem item : data) {
            SalesDataStatisticCache cache = convertItemToCache(item, startTime, endTime, now);
            cacheList.add(cache);
        }
        
        salesDataStatisticCacheRepository.saveAll(cacheList);
        log.info("更新缓存成功，保存 {} 条记录", cacheList.size());
    }
    
    /**
     * 为指定时间范围类型更新缓存
     */
    private void updateCacheForTimeRange(List<AdminUserRespDTO> salesUsers, TimeRangeTypeEnum type) {
        log.info("更新 {} 缓存", type.getName());
        
        TimeRangeUtil.TimeRange timeRange = TimeRangeUtil.calculateTimeRange(type);
        List<SalesDataItem> data = calculateSalesDataInRealTime(salesUsers, 
                timeRange.getStartTime(), timeRange.getEndTime());
        
        if (!data.isEmpty()) {
            updateCacheWithData(data, timeRange.getStartTime(), timeRange.getEndTime());
        }
    }

    /**
     * 检查指定时间范围是否正在刷新
     */
    @Override
    public boolean isRefreshing(SalesDataStatisticReqVO reqVO) {
        // 识别时间范围类型
        TimeRangeTypeEnum rangeType = TimeRangeUtil.matchTimeRangeType(reqVO.getStartTime(), reqVO.getEndTime());
        
        if (rangeType == TimeRangeTypeEnum.CUSTOM || !isCacheableRange(rangeType)) {
            // 非预定义范围，不涉及刷新
            return false;
        }
        
        // 检查该时间范围类型是否正在刷新
        String lockKey = rangeType.name();
        return refreshingRanges.containsKey(lockKey);
    }
    
    /**
     * 计算单个销售人员的数据（带时间范围）
     * 
     * 业务逻辑说明：
     * 1. 成交金额：时间范围内签订的合同总金额
     * 2. 应收金额：时间范围内签订的合同，到目前为止还欠多少钱（合同总金额 - 合同累计已收金额，不限时间）
     * 3. 已开票金额：时间范围内开票的总金额
     * 4. 回款金额：时间范围内回款的总金额
     */
    private SalesDataItem calculateSalesDataWithTimeRange(Long userId, String userName, 
                                                         LocalDateTime startTime, LocalDateTime endTime) {
        SalesDataItem item = SalesDataItem.builder()
            .userId(userId)
            .userName(userName)
            .orderAmount(BigDecimal.ZERO)
            .accountsReceivable(BigDecimal.ZERO)
            .invoiceAmount(BigDecimal.ZERO)
            .paymentAmount(BigDecimal.ZERO)
            .build();
        
        log.debug("解析后的时间范围: {} ~ {}", startTime, endTime);
        
        // ========== 1. 计算成交金额和应收金额 ==========
        // 查询该销售的所有已签订合同
        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository
                .findByStatusAndSalesIdIn(ProjectContractStatusEnums.SIGNED.getStatus(), new Long[]{userId});

        BigDecimal orderAmount = BigDecimal.ZERO; // 时间范围内签订的合同总金额
        BigDecimal contractTotalReceivedAmount = BigDecimal.ZERO; // 这些合同的累计已收金额（不限时间）
        int contractCount = 0;
        
        for (ProjectConstractOnly contract : contractList) {
            // 必须有签订时间才能参与统计
            if (contract.getSignedTime() == null) {
                log.warn("合同ID {} 没有签订时间，跳过统计", contract.getId());
                continue;
            }
            
            // 判断合同签订时间是否在指定范围内
            if (startTime != null && contract.getSignedTime().isBefore(startTime)) {
                continue; // 签订时间早于开始时间
            }
            if (endTime != null && contract.getSignedTime().isAfter(endTime)) {
                continue; // 签订时间晚于结束时间
            }
            
            // 该合同在时间范围内签订，计入统计
            contractCount++;
            if (contract.getPrice() != null) {
                orderAmount = orderAmount.add(contract.getPrice());
            }
            // 累加该合同到目前为止的累计已收金额（不限时间范围）
            if (contract.getReceivedPrice() != null) {
                contractTotalReceivedAmount = contractTotalReceivedAmount.add(contract.getReceivedPrice());
            }
            log.debug("合同: {} (ID: {}) - 金额: {}, 已收: {}", 
                contract.getName(), contract.getId(), contract.getPrice(), contract.getReceivedPrice());
        }
        
        log.debug("时间范围内签订的合同数: {}, 成交金额: {}, 累计已收: {}", 
            contractCount, orderAmount, contractTotalReceivedAmount);
        
        item.setOrderAmount(orderAmount);
        // 应收金额 = 这些合同的总金额 - 这些合同的累计已收金额
        // 含义：这些合同到目前为止还欠多少钱
        item.setAccountsReceivable(orderAmount.subtract(contractTotalReceivedAmount));
        
        // ========== 2. 计算已开票金额 ==========
        // 统计时间范围内开票的总金额（与合同签订时间无关）
        BigDecimal invoiceAmount = BigDecimal.ZERO;
        int invoiceCount = 0;
        
        List<cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLogOnly> invoiceList = 
            contractInvoiceLogOnlyRepository.findByStatusNotAndSalesIdIn(
                ContractInvoiceStatusEnums.NOT_INVOICE.getStatus(), List.of(userId));
        
        for (ContractInvoiceLogOnly invoiceLog : invoiceList) {
            // 必须有开票时间才能参与统计
            if (invoiceLog.getDate() == null) {
                log.warn("开票记录ID {} 没有开票时间，跳过统计", invoiceLog.getId());
                continue;
            }
            
            // 判断开票时间是否在指定范围内
            if (startTime != null && invoiceLog.getDate().isBefore(startTime)) {
                continue; // 开票时间早于开始时间
            }
            if (endTime != null && invoiceLog.getDate().isAfter(endTime)) {
                continue; // 开票时间晚于结束时间
            }
            
            // 该开票记录在时间范围内，计入统计
            invoiceCount++;
            if (invoiceLog.getPrice() != null) {
                invoiceAmount = invoiceAmount.add(invoiceLog.getPrice());
            }
        }
        
        log.debug("时间范围内的开票数: {}, 开票金额: {}", invoiceCount, invoiceAmount);
        item.setInvoiceAmount(invoiceAmount);
        
        // ========== 3. 计算回款金额 ==========
        // 统计时间范围内回款的总金额（与合同签订时间无关）
        BigDecimal paymentAmount = BigDecimal.ZERO;
        int paymentCount = 0;
        
        List<cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLogOnly> fundList = 
            contractFundLogOnlyRepository.findByStatusAndSalesIdIn(
                ContractFundStatusEnums.AUDITED.getStatus(), List.of(userId));
        
        for (var fundLog : fundList) {
            // 必须有回款时间才能参与统计
            if (fundLog.getPaidTime() == null) {
                log.warn("回款记录ID {} 没有回款时间，跳过统计", fundLog.getId());
                continue;
            }
            
            // 判断回款时间是否在指定范围内
            if (startTime != null && fundLog.getPaidTime().isBefore(startTime)) {
                continue; // 回款时间早于开始时间
            }
            if (endTime != null && fundLog.getPaidTime().isAfter(endTime)) {
                continue; // 回款时间晚于结束时间
            }
            
            // 该回款记录在时间范围内，计入统计
            paymentCount++;
            if (fundLog.getReceivedPrice() != null) {
                paymentAmount = paymentAmount.add(fundLog.getReceivedPrice());
            }
        }
        
        log.debug("时间范围内的回款数: {}, 回款金额: {}", paymentCount, paymentAmount);
        item.setPaymentAmount(paymentAmount);
        
        return item;
    }
    
    /**
     * 将缓存对象转换为数据项
     */
    private SalesDataItem convertCacheToItem(SalesDataStatisticCache cache) {
        return SalesDataItem.builder()
            .userId(cache.getUserId())
            .userName(cache.getUserName())
            .orderAmount(cache.getOrderAmount())
            .accountsReceivable(cache.getAccountsReceivable())
            .invoiceAmount(cache.getInvoiceAmount())
            .paymentAmount(cache.getPaymentAmount())
            .updateTime(cache.getCacheUpdateTime())
            .build();
    }
    
    /**
     * 将数据项转换为缓存对象
     */
    private SalesDataStatisticCache convertItemToCache(SalesDataItem item, LocalDateTime startTime, 
                                                      LocalDateTime endTime, LocalDateTime cacheUpdateTime) {
        SalesDataStatisticCache cache = new SalesDataStatisticCache();
        cache.setUserId(item.getUserId());
        cache.setUserName(item.getUserName());
        cache.setOrderAmount(item.getOrderAmount());
        cache.setAccountsReceivable(item.getAccountsReceivable());
        cache.setInvoiceAmount(item.getInvoiceAmount());
        cache.setPaymentAmount(item.getPaymentAmount());
        cache.setStartTime(startTime);
        cache.setEndTime(endTime);
        cache.setCacheUpdateTime(cacheUpdateTime);
        return cache;
    }
}


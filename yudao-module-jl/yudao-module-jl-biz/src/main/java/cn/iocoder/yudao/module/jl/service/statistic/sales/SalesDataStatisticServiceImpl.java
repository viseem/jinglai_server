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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 销售数据统计 Service 实现类
 */
@Service
@Validated
@Slf4j
public class SalesDataStatisticServiceImpl implements SalesDataStatisticService {

    // 更新状态标识（使用volatile保证可见性）
    private volatile boolean isUpdating = false;

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
    public List<SalesDataItem> getSalesDataStatistic(SalesDataStatisticReqVO reqVO) {
        log.info("===== 开始查询销售数据统计 =====");
        log.info("请求参数 - userIds: {}, startTime: {}, endTime: {}", 
            reqVO.getUserIds() != null ? java.util.Arrays.toString(reqVO.getUserIds()) : "null", 
            reqVO.getStartTime(), reqVO.getEndTime());
        
        // 获取时间范围
        LocalDateTime startTime = reqVO.getStartTime();
        LocalDateTime endTime = reqVO.getEndTime();
        
        log.info("查询时间范围: {} ~ {}", startTime, endTime);
        
        // 过滤掉无效的 userIds（0 或 null）
        List<Long> validUserIds = filterValidUserIds(reqVO.getUserIds());
        
        // 获取销售人员列表
        List<AdminUserRespDTO> salesUsers = getSalesUsers(validUserIds);
        if (salesUsers.isEmpty()) {
            log.warn("未找到销售人员");
            return new ArrayList<>();
        }
        
        // 优先尝试从缓存获取数据
        List<SalesDataItem> cachedData = tryGetFromCache(salesUsers, startTime, endTime);
        if (!cachedData.isEmpty()) {
            log.info("从缓存获取到 {} 条数据", cachedData.size());
            return cachedData;
        }
        
        // 缓存未命中，实时计算
        log.info("缓存未命中，开始实时计算");
        List<SalesDataItem> calculatedData = calculateSalesDataInRealTime(salesUsers, startTime, endTime);
        
        // 异步更新缓存（只缓存有数据的结果）
        if (!calculatedData.isEmpty()) {
            asyncUpdateCache(calculatedData, startTime, endTime);
        }
        
        log.info("返回 {} 条数据", calculatedData.size());
        log.info("===== 查询销售数据统计结束 =====");
        
        return calculatedData;
    }

    /**
     * 更新销售数据统计缓存（定时任务调用）- 更新常用时间范围的缓存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalesDataStatisticCache() {
        if (isUpdating) {
            log.warn("销售数据统计缓存正在更新中，跳过本次更新");
            throw new RuntimeException("数据正在更新中，请稍后再试");
        }
        
        try {
            isUpdating = true;
            log.info("========== 开始更新销售数据统计缓存 ==========");
            
            // 获取所有销售人员
            List<AdminUserRespDTO> salesUsers = adminUserApi.getUserListByRoleCode("sales");
            if (salesUsers == null || salesUsers.isEmpty()) {
                log.warn("未找到销售人员，跳过更新");
                return;
            }
            
            log.info("获取到 {} 个销售人员", salesUsers.size());
            
            // 更新常用时间范围的缓存
            TimeRangeTypeEnum[] commonTypes = {
                TimeRangeTypeEnum.TODAY,
                TimeRangeTypeEnum.YESTERDAY,
                TimeRangeTypeEnum.THIS_WEEK,
                TimeRangeTypeEnum.LAST_WEEK,
                TimeRangeTypeEnum.THIS_MONTH,
                TimeRangeTypeEnum.LAST_MONTH
            };
            
            for (TimeRangeTypeEnum type : commonTypes) {
                updateCacheForTimeRange(salesUsers, type);
            }
            
            log.info("========== 销售数据统计缓存更新完成 ==========");
        } catch (Exception e) {
            log.error("更新销售数据统计缓存失败", e);
            throw e;
        } finally {
            isUpdating = false;
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
        log.info("有效的 userIds: {}", validUserIds);
        return validUserIds;
    }
    
    /**
     * 获取销售人员列表
     */
    private List<AdminUserRespDTO> getSalesUsers(List<Long> validUserIds) {
        List<AdminUserRespDTO> salesUsers;
        if (validUserIds.isEmpty()) {
            // 未指定销售人员，查询所有拥有销售角色的人员
            salesUsers = adminUserApi.getUserListByRoleCode("sales");
            log.info("查询所有销售人员，共 {} 人", salesUsers != null ? salesUsers.size() : 0);
        } else {
            // 指定了销售人员，只查询这些人员
            salesUsers = adminUserApi.getUserList(validUserIds);
            log.info("查询指定销售人员，共 {} 人", salesUsers != null ? salesUsers.size() : 0);
        }
        return salesUsers != null ? salesUsers : new ArrayList<>();
    }
    
    /**
     * 尝试从缓存获取数据
     */
    private List<SalesDataItem> tryGetFromCache(List<AdminUserRespDTO> salesUsers, 
                                                LocalDateTime startTime, 
                                                LocalDateTime endTime) {
        List<SalesDataItem> result = new ArrayList<>();
        
        if (startTime != null && endTime != null) {
            // 根据精确时间范围查找缓存
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
            }
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
            log.info("计算用户 {} ({}) 在时间范围 [{} ~ {}] 的统计数据", 
                user.getId(), user.getNickname(), startTime, endTime);
            
            SalesDataItem item = calculateSalesDataWithTimeRange(user.getId(), user.getNickname(), startTime, endTime);
            item.setUpdateTime(now);
            result.add(item);
            
            log.info("用户 {} - 成交金额: {}, 应收金额: {}, 已开票金额: {}, 回款金额: {}", 
                user.getNickname(), item.getOrderAmount(), item.getAccountsReceivable(), 
                item.getInvoiceAmount(), item.getPaymentAmount());
        }
        
        return result;
    }
    
    /**
     * 异步更新缓存
     */
    private void asyncUpdateCache(List<SalesDataItem> data, LocalDateTime startTime, LocalDateTime endTime) {
        // 这里可以使用 @Async 注解进行异步处理，为简化先同步执行
        try {
            updateCacheWithData(data, startTime, endTime);
        } catch (Exception e) {
            log.error("更新缓存失败", e);
        }
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
     * 检查是否正在刷新
     */
    @Override
    public boolean isRefreshing() {
        return isUpdating;
    }
    
    /**
     * 计算单个销售人员的数据（带时间范围）
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
        
        // 计算成交金额和应收金额
        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository
                .findByStatusAndSalesIdIn(ProjectContractStatusEnums.SIGNED.getStatus(), new Long[]{userId});
        
        BigDecimal orderAmount = BigDecimal.ZERO;
        BigDecimal contractPaymentAmount = BigDecimal.ZERO;
        int contractCount = 0;
        
        for (ProjectConstractOnly contract : contractList) {
            // 判断合同签订时间是否在范围内
            if (contract.getSignedTime() != null) {
                if (startTime != null && contract.getSignedTime().isBefore(startTime)) {
                    continue;
                }
                if (endTime != null && contract.getSignedTime().isAfter(endTime)) {
                    continue;
                }
            }
            
            contractCount++;
            if (contract.getPrice() != null) {
                orderAmount = orderAmount.add(contract.getPrice());
            }
            if (contract.getReceivedPrice() != null) {
                contractPaymentAmount = contractPaymentAmount.add(contract.getReceivedPrice());
            }
        }
        
        log.debug("时间范围内的合同数: {}", contractCount);
        
        item.setOrderAmount(orderAmount);
        // 应收金额 = 成交金额 - 合同已收金额
        item.setAccountsReceivable(orderAmount.subtract(contractPaymentAmount));
        
        // 计算已开票金额（根据开票时间过滤）
        BigDecimal invoiceAmount = BigDecimal.ZERO;
        int invoiceCount = 0;
        
        List<cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLogOnly> invoiceList = 
            contractInvoiceLogOnlyRepository.findByStatusNotAndSalesIdIn(
                ContractInvoiceStatusEnums.NOT_INVOICE.getStatus(), List.of(userId));
        
        for (ContractInvoiceLogOnly invoiceLog : invoiceList) {
            // 判断开票时间是否在范围内
            if (invoiceLog.getDate() != null) {
                if (startTime != null && invoiceLog.getDate().isBefore(startTime)) {
                    continue;
                }
                if (endTime != null && invoiceLog.getDate().isAfter(endTime)) {
                    continue;
                }
            }
            
            invoiceCount++;
            if (invoiceLog.getPrice() != null) {
                invoiceAmount = invoiceAmount.add(invoiceLog.getPrice());
            }
        }
        
        log.debug("时间范围内的开票数: {}", invoiceCount);
        item.setInvoiceAmount(invoiceAmount);
        
        // 计算回款金额（根据回款时间过滤）
        BigDecimal paymentAmount = BigDecimal.ZERO;
        int paymentCount = 0;
        
        List<cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLogOnly> fundList = 
            contractFundLogOnlyRepository.findByStatusAndSalesIdIn(
                ContractFundStatusEnums.AUDITED.getStatus(), List.of(userId));
        
        for (var fundLog : fundList) {
            // 判断回款时间是否在范围内
            if (fundLog.getPaidTime() != null) {
                if (startTime != null && fundLog.getPaidTime().isBefore(startTime)) {
                    continue;
                }
                if (endTime != null && fundLog.getPaidTime().isAfter(endTime)) {
                    continue;
                }
            }
            
            paymentCount++;
            if (fundLog.getReceivedPrice() != null) {
                paymentAmount = paymentAmount.add(fundLog.getReceivedPrice());
            }
        }
        
        log.debug("时间范围内的回款数: {}", paymentCount);
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


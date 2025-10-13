package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticResp.SalesDataItem;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.statistic.SalesDataStatisticCache;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.statistic.SalesDataStatisticCacheRepository;
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
     * 获取销售数据统计（从缓存读取）
     */
    @Override
    public List<SalesDataItem> getSalesDataStatistic(SalesDataStatisticReqVO reqVO) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("===== 开始查询销售数据统计 =====");
        System.out.println("查询时间: " + now);
        System.out.println("请求参数 - userIds: " + (reqVO.getUserIds() != null ? java.util.Arrays.toString(reqVO.getUserIds()) : "null") 
            + ", startTime: " + reqVO.getStartTime() + ", endTime: " + reqVO.getEndTime());
        
        // 过滤掉无效的 userIds（0 或 null）
        Long[] validUserIds = null;
        if (reqVO.getUserIds() != null && reqVO.getUserIds().length > 0) {
            validUserIds = java.util.Arrays.stream(reqVO.getUserIds())
                .filter(userId -> userId != null && userId > 0) // 过滤掉 null 和 0
                .toArray(Long[]::new);
            
            if (validUserIds.length == 0) {
                validUserIds = null; // 如果过滤后为空，视为不筛选
                System.out.println("【提示】过滤掉无效的 userIds，视为查询全部");
            } else {
                System.out.println("【提示】有效的 userIds: " + java.util.Arrays.toString(validUserIds));
            }
        }
        
        // 从缓存读取数据（查询今天的数据）
        List<SalesDataStatisticCache> cacheList = salesDataStatisticCacheRepository.findByStatisticDate(now);
        
        System.out.println("从缓存中查询到 " + (cacheList != null ? cacheList.size() : 0) + " 条数据");
        
        if (cacheList != null && !cacheList.isEmpty()) {
            System.out.println("缓存数据详情:");
            for (SalesDataStatisticCache cache : cacheList) {
                System.out.println("  - userId: " + cache.getUserId() 
                    + ", userName: " + cache.getUserName() 
                    + ", statisticDate: " + cache.getStatisticDate()
                    + ", updateTime: " + cache.getUpdateTime()
                    + ", deleted: " + cache.getDeleted());
            }
        }
        
        // 如果缓存为空，自动触发刷新
        if (cacheList == null || cacheList.isEmpty()) {
            System.out.println("【提示】缓存数据为空，自动触发数据刷新");
            
            // 检查是否正在更新，如果正在更新则不重复刷新
            if (!isUpdating) {
                try {
                    // 自动刷新缓存
                    updateSalesDataStatisticCache();
                    
                    // 刷新后重新查询缓存
                    cacheList = salesDataStatisticCacheRepository.findByStatisticDate(now);
                    System.out.println("刷新后从缓存中查询到 " + (cacheList != null ? cacheList.size() : 0) + " 条数据");
                } catch (Exception e) {
                    System.out.println("【错误】自动刷新失败: " + e.getMessage());
                    // 刷新失败也返回空列表
                    return new ArrayList<>();
                }
            } else {
                System.out.println("【提示】数据正在刷新中，稍后请重新查询");
                return new ArrayList<>();
            }
        }
        
        // 转换为响应对象
        List<SalesDataItem> respList = new ArrayList<>();
        
        if (cacheList == null || cacheList.isEmpty()) {
            System.out.println("【警告】缓存数据仍为空");
            return respList;
        }
        
        for (SalesDataStatisticCache cache : cacheList) {
            // 如果指定了有效的userIds，进行过滤
            if (validUserIds != null && validUserIds.length > 0) {
                boolean found = false;
                for (Long userId : validUserIds) {
                    if (userId.equals(cache.getUserId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("过滤掉 userId: " + cache.getUserId() + " (" + cache.getUserName() + ")");
                    continue;
                }
            }
            
            SalesDataItem item = SalesDataItem.builder()
                .userId(cache.getUserId())
                .userName(cache.getUserName())
                .orderAmount(cache.getOrderAmount())
                .accountsReceivable(cache.getAccountsReceivable())
                .invoiceAmount(cache.getInvoiceAmount())
                .paymentAmount(cache.getPaymentAmount())
                .updateTime(cache.getUpdateTime())
                .build();
            respList.add(item);
        }
        
        System.out.println("过滤后返回 " + respList.size() + " 条数据");
        System.out.println("===== 查询销售数据统计结束 =====");
        
        return respList;
    }

    /**
     * 更新销售数据统计缓存（定时任务调用）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSalesDataStatisticCache() {
        // 检查是否正在更新
        if (isUpdating) {
            System.out.println("【警告】销售数据统计缓存正在更新中，跳过本次更新");
            throw new RuntimeException("数据正在更新中，请稍后再试");
        }
        
        try {
            // 设置更新状态
            isUpdating = true;
            System.out.println("========== 开始更新销售数据统计缓存 ==========");
            
            // 获取所有销售人员（拥有"销售"角色）
            List<AdminUserRespDTO> salesUsers = adminUserApi.getUserListByRoleCode("sales");
            
            System.out.println("从销售角色获取到 " + (salesUsers != null ? salesUsers.size() : 0) + " 个销售人员");
            
            if (salesUsers == null || salesUsers.isEmpty()) {
                System.out.println("【警告】未找到销售人员，跳过更新");
                return;
            }
            
            // 打印销售人员列表
            System.out.println("销售人员列表:");
            for (AdminUserRespDTO user : salesUsers) {
                System.out.println("  - userId: " + user.getId() + ", nickname: " + user.getNickname());
            }
            
            // 获取当前时间（精确到秒）
            LocalDateTime now = LocalDateTime.now();
            System.out.println("统计时间: " + now);
            
            // 删除今天的旧缓存数据
            System.out.println("删除今天的旧缓存数据...");
            salesDataStatisticCacheRepository.deleteByStatisticDate(now);
            
            // 为每个销售人员计算统计数据
            List<SalesDataStatisticCache> cacheList = new ArrayList<>();
            
            for (AdminUserRespDTO user : salesUsers) {
                System.out.println("计算用户 " + user.getId() + " (" + user.getNickname() + ") 的统计数据...");
                SalesDataStatisticCache cache = calculateSalesData(user.getId(), user.getNickname(), now);
                System.out.println("  成交金额: " + cache.getOrderAmount() 
                    + ", 应收金额: " + cache.getAccountsReceivable() 
                    + ", 已开票金额: " + cache.getInvoiceAmount() 
                    + ", 回款金额: " + cache.getPaymentAmount());
                cacheList.add(cache);
            }
            
            // 批量保存
            System.out.println("批量保存 " + cacheList.size() + " 条缓存数据...");
            List<SalesDataStatisticCache> savedList = salesDataStatisticCacheRepository.saveAll(cacheList);
            System.out.println("实际保存成功 " + savedList.size() + " 条数据");
            
            // 验证保存结果
            List<SalesDataStatisticCache> verifyList = salesDataStatisticCacheRepository.findByStatisticDate(now);
            System.out.println("验证查询：数据库中现在有 " + (verifyList != null ? verifyList.size() : 0) + " 条今天的缓存数据");
            
            System.out.println("========== 销售数据统计缓存更新完成 ==========");
        } catch (Exception e) {
            System.out.println("【错误】更新销售数据统计缓存失败: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            // 无论成功失败，都要重置更新状态
            isUpdating = false;
        }
    }

    /**
     * 计算单个销售人员的数据
     */
    private SalesDataStatisticCache calculateSalesData(Long userId, String userName, LocalDateTime statisticDate) {
        SalesDataStatisticCache cache = new SalesDataStatisticCache();
        cache.setUserId(userId);
        cache.setUserName(userName);
        cache.setStatisticDate(statisticDate);
        
        // 计算成交金额和应收金额
        // 查询该销售人员的所有已签订合同
        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository
                .findByStatusAndSalesIdIn(ProjectContractStatusEnums.SIGNED.getStatus(), new Long[]{userId});
        
        BigDecimal orderAmount = BigDecimal.ZERO;
        BigDecimal contractPaymentAmount = BigDecimal.ZERO;
        
        for (ProjectConstractOnly contract : contractList) {
            if (contract.getPrice() != null) {
                orderAmount = orderAmount.add(contract.getPrice());
            }
            if (contract.getReceivedPrice() != null) {
                contractPaymentAmount = contractPaymentAmount.add(contract.getReceivedPrice());
            }
        }
        
        cache.setOrderAmount(orderAmount);
        // 应收金额 = 成交金额 - 合同已收金额
        cache.setAccountsReceivable(orderAmount.subtract(contractPaymentAmount));
        
        // 计算已开票金额
        contractInvoiceLogOnlyRepository
                .findByStatusNotAndSalesIdIn(ContractInvoiceStatusEnums.NOT_INVOICE.getStatus(), List.of(userId))
                .forEach(invoiceLog -> {
                    if (invoiceLog.getPrice() != null) {
                        cache.setInvoiceAmount(cache.getInvoiceAmount().add(invoiceLog.getPrice()));
                    }
                });
        
        // 计算回款金额
        contractFundLogOnlyRepository
                .findByStatusAndSalesIdIn(ContractFundStatusEnums.AUDITED.getStatus(), List.of(userId))
                .forEach(fundLog -> {
                    if (fundLog.getReceivedPrice() != null) {
                        cache.setPaymentAmount(cache.getPaymentAmount().add(fundLog.getReceivedPrice()));
                    }
                });
        
        return cache;
    }

    /**
     * 检查是否正在刷新
     */
    @Override
    public boolean isRefreshing() {
        return isUpdating;
    }
}


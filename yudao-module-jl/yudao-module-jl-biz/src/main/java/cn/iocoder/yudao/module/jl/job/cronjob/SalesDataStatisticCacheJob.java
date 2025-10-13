package cn.iocoder.yudao.module.jl.job.cronjob;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.jl.service.statistic.sales.SalesDataStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 销售数据统计缓存更新 Job
 * 每日凌晨自动更新销售数据统计缓存
 * 
 * 执行时间：每天凌晨 00:30
 * Cron表达式：0 30 0 * * ?
 * 
 * @author jinglai
 */
@Component
@TenantJob // 多租户
@Slf4j
public class SalesDataStatisticCacheJob implements JobHandler {

    @Resource
    private SalesDataStatisticService salesDataStatisticService;

    @Override
    public String execute(String param) throws Exception {
        log.info("开始执行销售数据统计缓存更新任务");
        
        try {
            // 调用服务层方法更新缓存
            salesDataStatisticService.updateSalesDataStatisticCache();
            
            String result = "销售数据统计缓存更新成功";
            log.info(result);
            return result;
            
        } catch (Exception e) {
            String errorMsg = "销售数据统计缓存更新失败: " + e.getMessage();
            log.error(errorMsg, e);
            throw new Exception(errorMsg, e);
        }
    }
}


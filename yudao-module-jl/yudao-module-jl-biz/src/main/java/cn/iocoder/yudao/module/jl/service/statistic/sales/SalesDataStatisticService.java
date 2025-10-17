package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesDataStatisticResp;

import java.util.List;

/**
 * 销售数据统计 Service 接口
 */
public interface SalesDataStatisticService {

    /**
     * 获取销售数据统计（从缓存读取）
     * 返回的是数据项列表，不包含刷新状态（刷新状态由Controller层添加）
     */
    List<SalesDataStatisticResp.SalesDataItem> getSalesDataStatistic(SalesDataStatisticReqVO reqVO);

    /**
     * 更新销售数据统计缓存（定时任务调用）
     */
    void updateSalesDataStatisticCache();
    
    /**
     * 异步更新销售数据统计缓存（手动刷新调用）
     */
    void updateSalesDataStatisticCacheAsync(SalesDataStatisticReqVO reqVO);

    /**
     * 检查指定时间范围是否正在刷新
     */
    boolean isRefreshing(SalesDataStatisticReqVO reqVO);
    
    /**
     * 检查指定时间范围是否正在查询中（实时计算）
     */
    boolean isQuerying(SalesDataStatisticReqVO reqVO);
}


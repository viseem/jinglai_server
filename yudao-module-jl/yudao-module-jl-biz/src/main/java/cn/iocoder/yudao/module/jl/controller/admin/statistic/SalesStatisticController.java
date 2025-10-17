package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.*;
import cn.iocoder.yudao.module.jl.service.statistic.sales.SalesStatisticService;
import cn.iocoder.yudao.module.jl.service.statistic.sales.SalesDataStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "销售端数据统计")
@RestController
@RequestMapping("/statistic/sales")
@Validated
public class SalesStatisticController {

    @Resource
    private SalesStatisticService salesStatisticService;

    @Resource
    private SalesDataStatisticService salesDataStatisticService;

    @GetMapping("/followup-count")
    @Operation(summary = "获取跟进的统计数据")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SalesStatisticFollowupResp> getSubjectGroupFollowupStats(@Valid SalesStatisticReqVO reqVO) {
        SalesStatisticFollowupResp salesStatisticFollowupResp = salesStatisticService.countFollowup(reqVO);
        return success(salesStatisticFollowupResp);
    }

    @GetMapping("/saleslead-count")
    @Operation(summary = "获取商机的统计数据")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SalesStatisticSalesleadResp> getSubjectGroupSalesleadStats(@Valid SalesStatisticReqVO reqVO) {
        SalesStatisticSalesleadResp salesStatisticSalesleadResp = salesStatisticService.countSaleslead(reqVO);
        return success(salesStatisticSalesleadResp);
    }

    @GetMapping("/saleslead-count-month")
    @Operation(summary = "获取商机的统计数据")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SalesStatisticSalesleadMonthResp> getSubjectGroupSalesleadStatsMonth(@Valid SalesStatisticReqVO reqVO) {
        SalesStatisticSalesleadMonthResp salesStatisticSalesleadMonthResp = salesStatisticService.countSalesleadMonth(reqVO);
        return success(salesStatisticSalesleadMonthResp);
    }

    @GetMapping("/group-stats-order")
    @Operation(summary = "获取分组的统计数据")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SalesGroupStatisticResp> getSalesGroupStatsOrder(@Valid SalesGroupStatisticReqVO reqVO) {
        SalesGroupStatisticResp salesGroupStatisticResp = salesStatisticService.groupStatsOrder(reqVO);
        return success(salesGroupStatisticResp);
    }

    @GetMapping("/group-stats-refund")
    @Operation(summary = "获取分组的统计数据")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SalesGroupStatisticResp> getSalesGroupStatsRefund(@Valid SalesGroupStatisticReqVO reqVO) {
        SalesGroupStatisticResp salesGroupStatisticResp = salesStatisticService.groupStatsRefund(reqVO);
        return success(salesGroupStatisticResp);
    }

    @GetMapping("/group-stats-not-pay")
    @Operation(summary = "获取分组的统计数据 全部日期")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SalesGroupStatisticResp> getSalesGroupStatsAllDate(@Valid SalesGroupStatisticReqVO reqVO) {
        SalesGroupStatisticResp salesGroupStatisticResp = salesStatisticService.groupStatsNotPay(reqVO);
        return success(salesGroupStatisticResp);
    }

    @GetMapping("/sales-data-statistic")
    @Operation(summary = "获取销售数据统计")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SalesDataStatisticResp> getSalesDataStatistic(@Valid SalesDataStatisticReqVO reqVO) {
        // 检查该时间范围是否正在刷新
        boolean isRefreshing = salesDataStatisticService.isRefreshing(reqVO);
        
        // 检查该时间范围是否正在查询中
        boolean isQuerying = salesDataStatisticService.isQuerying(reqVO);
        
        // 获取数据（已经是SalesDataItem列表）
        List<SalesDataStatisticResp.SalesDataItem> items = salesDataStatisticService.getSalesDataStatistic(reqVO);
        
        // 构建响应（包含数据、刷新状态和查询状态）
        SalesDataStatisticResp resp = SalesDataStatisticResp.builder()
            .isRefreshing(isRefreshing)
            .isQuerying(isQuerying)
            .data(items)
            .build();
        
        return success(resp);
    }

    @GetMapping("/sales-data-statistic-refresh")
    @Operation(summary = "手动刷新销售数据统计缓存")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<Boolean> refreshSalesDataStatistic(@Valid SalesDataStatisticReqVO reqVO) {
        // 检查该时间范围是否已经有任务在执行
        if (salesDataStatisticService.isRefreshing(reqVO)) {
            throw new RuntimeException("该时间范围数据正在更新中，请稍后再试");
        }
        
        // 异步执行刷新任务（传入时间范围）
        salesDataStatisticService.updateSalesDataStatisticCacheAsync(reqVO);
        return success(true);
    }

    @GetMapping("/sales-data-statistic-refresh-status")
    @Operation(summary = "检查销售数据刷新状态（轻量级接口，仅返回状态）")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<Boolean> getRefreshStatus(@Valid SalesDataStatisticReqVO reqVO) {
        // 直接从内存读取刷新状态，不查询数据
        boolean isRefreshing = salesDataStatisticService.isRefreshing(reqVO);
        return success(isRefreshing);
    }
}

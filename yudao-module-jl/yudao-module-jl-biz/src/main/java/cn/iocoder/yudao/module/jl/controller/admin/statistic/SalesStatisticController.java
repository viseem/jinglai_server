package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.*;
import cn.iocoder.yudao.module.jl.service.statistic.sales.SalesStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "销售端数据统计")
@RestController
@RequestMapping("/statistic/sales")
@Validated
public class SalesStatisticController {

    @Resource
    private SalesStatisticService salesStatisticService;

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
}

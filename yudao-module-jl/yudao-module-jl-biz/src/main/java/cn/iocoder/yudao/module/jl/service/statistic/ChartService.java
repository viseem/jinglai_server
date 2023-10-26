package cn.iocoder.yudao.module.jl.service.statistic;


import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.*;

/**
 * 友商 Service 接口
 *
 */
public interface ChartService {


    // 回款统计
    ChartRefundStatsResp getRefundStats(ChartRefundStatsReqVO reqVO);

    // 开票统计
    ChartInvoiceStatsResp getInvoiceStats(ChartInvoiceStatsReqVO reqVO);

    // 合同统计
    ChartContractStatsResp getContractStats(ChartContractStatsReqVO reqVO);

    // 商机统计
    ChartSalesleadStatsResp getSalesleadStats(ChartSalesleadStatsReqVO reqVO);

    // 项目统计
    ChartProjectStatsResp getProjectStats(ChartProjectStatsReqVO reqVO);

}

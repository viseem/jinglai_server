package cn.iocoder.yudao.module.jl.service.statistic;


import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartContractStatsReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartContractStatsResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartRefundStatsReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartRefundStatsResp;

/**
 * 友商 Service 接口
 *
 */
public interface ChartService {


    // 回款统计
    ChartRefundStatsResp getRefundStats(ChartRefundStatsReqVO reqVO);

    // 合同统计
    ChartContractStatsResp getContractStats(ChartContractStatsReqVO reqVO);

}

package cn.iocoder.yudao.module.jl.service.statistic;


import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationProjectCountStatsResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationSaleCountStatsResp;

/**
 * 友商 Service 接口
 *
 */
public interface WorkstationService {

    // 销售工作台的数字统计
    WorkstationSaleCountStatsResp getSaleCountStats();

    //项目工作台的数字统计
    WorkstationProjectCountStatsResp getProjectCountStats();

}

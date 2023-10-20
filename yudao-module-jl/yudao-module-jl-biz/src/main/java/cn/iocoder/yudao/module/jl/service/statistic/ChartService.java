package cn.iocoder.yudao.module.jl.service.statistic;


import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.*;

/**
 * 友商 Service 接口
 *
 */
public interface ChartService {

    // 销售工作台的数字统计
    WorkstationSaleCountStatsResp getSaleCountStats();

    //项目工作台的数字统计
    WorkstationProjectCountStatsResp getProjectCountStats();

    //实验人员工作台的数字统计
    WorkstationExpCountStatsResp getExpCountStats();

    //财务人员工作台的数字统计
    WorkstationFinanceCountStatsResp getFinanceCountStats();

    //库管人员工作台的数字统计
    WorkstationWarehouseCountStatsResp getWarehouseCountStats();

}

package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticFollowupResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticSalesleadResp;

/**
 * 专题小组 Service 接口
 *
 */
public interface SalesStatisticService {


    SalesStatisticFollowupResp countFollowup(SalesStatisticReqVO reqVO);

    SalesStatisticSalesleadResp countSaleslead(SalesStatisticReqVO reqVO);
}

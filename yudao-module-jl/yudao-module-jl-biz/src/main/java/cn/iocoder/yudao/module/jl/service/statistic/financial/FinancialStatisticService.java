package cn.iocoder.yudao.module.jl.service.statistic.financial;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.financial.FinancialReceivableStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.*;

import java.util.List;

/**
 * 专题小组 Service 接口
 *
 */
public interface FinancialStatisticService {


    FinancialStatisticResp accountsReceivable(FinancialReceivableStatisticReqVO reqVO);

    List<FinancialStatisticResp> accountsReceivableMonth(FinancialReceivableStatisticReqVO reqVO);

}

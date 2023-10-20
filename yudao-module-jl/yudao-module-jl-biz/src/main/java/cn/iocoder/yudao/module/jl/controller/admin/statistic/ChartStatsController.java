package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartContractStatsReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartContractStatsResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartRefundStatsReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.ChartRefundStatsResp;
import cn.iocoder.yudao.module.jl.service.statistic.ChartService;
import cn.iocoder.yudao.module.jl.service.statistic.WorkstationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "数据统计 - 工作台")
@RestController
@RequestMapping("/jl/chart-stats")
@Validated
public class ChartStatsController {

    @Resource
    private ChartService chartService;

    @GetMapping("/refund")
    public CommonResult<ChartRefundStatsResp> getStats(@Valid ChartRefundStatsReqVO reqVO) {
        ChartRefundStatsResp refundStats = chartService.getRefundStats(reqVO);
        return success(refundStats);
    }

    @GetMapping("/contract")
    public CommonResult<ChartContractStatsResp> getStats(@Valid ChartContractStatsReqVO reqVO) {
        ChartContractStatsResp contractStats = chartService.getContractStats(reqVO);
        return success(contractStats);
    }



}

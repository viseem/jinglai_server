package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.*;
import cn.iocoder.yudao.module.jl.service.statistic.WorkstationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "数据统计 - 工作台")
@RestController
@RequestMapping("/jl/workstation-stats")
@Validated
public class WorkstationCountStatsController {

    @Resource
    private WorkstationService workstationService;

    @GetMapping("/sale")
    public CommonResult<WorkstationSaleCountStatsResp> getSaleCountStats() {
        WorkstationSaleCountStatsResp saleCountStats = workstationService.getSaleCountStats();
        return success(saleCountStats);
    }

    @GetMapping("/project")
    public CommonResult<WorkstationProjectCountStatsResp> getProjectCountStats() {
        WorkstationProjectCountStatsResp projectCountStats = workstationService.getProjectCountStats();
        return success(projectCountStats);
    }

    @GetMapping("/exp")
    public CommonResult<WorkstationExpCountStatsResp> getExpCountStats() {
        WorkstationExpCountStatsResp expCountStatsResp = workstationService.getExpCountStats();
        return success(expCountStatsResp);
    }

    @GetMapping("/finance")
    public CommonResult<WorkstationFinanceCountStatsResp> getFinanceCountStats() {
        WorkstationFinanceCountStatsResp financeCountStatsResp = workstationService.getFinanceCountStats();
        return success(financeCountStatsResp);
    }

    @GetMapping("/warehouse")
    public CommonResult<WorkstationWarehouseCountStatsResp> getWarehouseCountStats() {
        WorkstationWarehouseCountStatsResp countStats = workstationService.getWarehouseCountStats();
        return success(countStats);
    }
}

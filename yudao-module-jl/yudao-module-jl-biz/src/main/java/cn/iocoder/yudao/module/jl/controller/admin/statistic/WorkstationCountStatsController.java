package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationSaleCountStatsResp;
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
    @Operation(summary = "通过 ID 获得友商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:competitor:query')")
    public CommonResult<WorkstationSaleCountStatsResp> getSaleCountStats(@RequestParam("id") Long id) {
        WorkstationSaleCountStatsResp saleCountStats = workstationService.getSaleCountStats();
        return success(saleCountStats);
    }
}

package cn.iocoder.yudao.module.jl.controller.admin.project;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyLog;
import cn.iocoder.yudao.module.jl.mapper.project.SupplyLogMapper;
import cn.iocoder.yudao.module.jl.service.project.SupplyLogService;

@Tag(name = "管理后台 - 项目物资变更日志")
@RestController
@RequestMapping("/jl/supply-log")
@Validated
public class SupplyLogController {

    @Resource
    private SupplyLogService supplyLogService;

    @Resource
    private SupplyLogMapper supplyLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目物资变更日志")
    @PreAuthorize("@ss.hasPermission('jl:supply-log:create')")
    public CommonResult<Long> createSupplyLog(@Valid @RequestBody SupplyLogCreateReqVO createReqVO) {
        return success(supplyLogService.createSupplyLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目物资变更日志")
    @PreAuthorize("@ss.hasPermission('jl:supply-log:update')")
    public CommonResult<Boolean> updateSupplyLog(@Valid @RequestBody SupplyLogUpdateReqVO updateReqVO) {
        supplyLogService.updateSupplyLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目物资变更日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-log:delete')")
    public CommonResult<Boolean> deleteSupplyLog(@RequestParam("id") Long id) {
        supplyLogService.deleteSupplyLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目物资变更日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-log:query')")
    public CommonResult<SupplyLogRespVO> getSupplyLog(@RequestParam("id") Long id) {
            Optional<SupplyLog> supplyLog = supplyLogService.getSupplyLog(id);
        return success(supplyLog.map(supplyLogMapper::toDto).orElseThrow(() -> exception(SUPPLY_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目物资变更日志列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-log:query')")
    public CommonResult<PageResult<SupplyLogRespVO>> getSupplyLogPage(@Valid SupplyLogPageReqVO pageVO, @Valid SupplyLogPageOrder orderV0) {
        PageResult<SupplyLog> pageResult = supplyLogService.getSupplyLogPage(pageVO, orderV0);
        return success(supplyLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目物资变更日志 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-log:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplyLogExcel(@Valid SupplyLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplyLog> list = supplyLogService.getSupplyLogList(exportReqVO);
        // 导出 Excel
        List<SupplyLogExcelVO> excelData = supplyLogMapper.toExcelList(list);
        ExcelUtils.write(response, "项目物资变更日志.xls", "数据", SupplyLogExcelVO.class, excelData);
    }

}

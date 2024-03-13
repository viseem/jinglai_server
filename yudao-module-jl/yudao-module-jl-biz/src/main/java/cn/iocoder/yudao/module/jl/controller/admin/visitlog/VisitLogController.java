package cn.iocoder.yudao.module.jl.controller.admin.visitlog;

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

import cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitlog.VisitLog;
import cn.iocoder.yudao.module.jl.mapper.visitlog.VisitLogMapper;
import cn.iocoder.yudao.module.jl.service.visitlog.VisitLogService;

@Tag(name = "管理后台 - 拜访记录")
@RestController
@RequestMapping("/jl/visit-log")
@Validated
public class VisitLogController {

    @Resource
    private VisitLogService visitLogService;

    @Resource
    private VisitLogMapper visitLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建拜访记录")
    @PreAuthorize("@ss.hasPermission('jl:visit-log:create')")
    public CommonResult<Long> createVisitLog(@Valid @RequestBody VisitLogCreateReqVO createReqVO) {
        return success(visitLogService.createVisitLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新拜访记录")
    @PreAuthorize("@ss.hasPermission('jl:visit-log:update')")
    public CommonResult<Boolean> updateVisitLog(@Valid @RequestBody VisitLogUpdateReqVO updateReqVO) {
        visitLogService.updateVisitLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除拜访记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:visit-log:delete')")
    public CommonResult<Boolean> deleteVisitLog(@RequestParam("id") Long id) {
        visitLogService.deleteVisitLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得拜访记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:visit-log:query')")
    public CommonResult<VisitLogRespVO> getVisitLog(@RequestParam("id") Long id) {
            Optional<VisitLog> visitLog = visitLogService.getVisitLog(id);
        return success(visitLog.map(visitLogMapper::toDto).orElseThrow(() -> exception(VISIT_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得拜访记录列表")
    @PreAuthorize("@ss.hasPermission('jl:visit-log:query')")
    public CommonResult<PageResult<VisitLogRespVO>> getVisitLogPage(@Valid VisitLogPageReqVO pageVO, @Valid VisitLogPageOrder orderV0) {
        PageResult<VisitLog> pageResult = visitLogService.getVisitLogPage(pageVO, orderV0);
        return success(visitLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出拜访记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:visit-log:export')")
    @OperateLog(type = EXPORT)
    public void exportVisitLogExcel(@Valid VisitLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<VisitLog> list = visitLogService.getVisitLogList(exportReqVO);
        // 导出 Excel
        List<VisitLogExcelVO> excelData = visitLogMapper.toExcelList(list);
        ExcelUtils.write(response, "拜访记录.xls", "数据", VisitLogExcelVO.class, excelData);
    }

}

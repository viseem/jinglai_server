package cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog;

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

import cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.quotationchangelog.QuotationChangeLog;
import cn.iocoder.yudao.module.jl.mapper.quotationchangelog.QuotationChangeLogMapper;
import cn.iocoder.yudao.module.jl.service.quotationchangelog.QuotationChangeLogService;

@Tag(name = "管理后台 - 报价变更日志")
@RestController
@RequestMapping("/jl/quotation-change-log")
@Validated
public class QuotationChangeLogController {

    @Resource
    private QuotationChangeLogService quotationChangeLogService;

    @Resource
    private QuotationChangeLogMapper quotationChangeLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建报价变更日志")
    @PreAuthorize("@ss.hasPermission('jl:quotation-change-log:create')")
    public CommonResult<Long> createQuotationChangeLog(@Valid @RequestBody QuotationChangeLogCreateReqVO createReqVO) {
        return success(quotationChangeLogService.createQuotationChangeLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报价变更日志")
    @PreAuthorize("@ss.hasPermission('jl:quotation-change-log:update')")
    public CommonResult<Boolean> updateQuotationChangeLog(@Valid @RequestBody QuotationChangeLogUpdateReqVO updateReqVO) {
        quotationChangeLogService.updateQuotationChangeLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除报价变更日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:quotation-change-log:delete')")
    public CommonResult<Boolean> deleteQuotationChangeLog(@RequestParam("id") Long id) {
        quotationChangeLogService.deleteQuotationChangeLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得报价变更日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:quotation-change-log:query')")
    public CommonResult<QuotationChangeLogRespVO> getQuotationChangeLog(@RequestParam("id") Long id) {
            Optional<QuotationChangeLog> quotationChangeLog = quotationChangeLogService.getQuotationChangeLog(id);
        return success(quotationChangeLog.map(quotationChangeLogMapper::toDto).orElseThrow(() -> exception(QUOTATION_CHANGE_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得报价变更日志列表")
    @PreAuthorize("@ss.hasPermission('jl:quotation-change-log:query')")
    public CommonResult<PageResult<QuotationChangeLogRespVO>> getQuotationChangeLogPage(@Valid QuotationChangeLogPageReqVO pageVO, @Valid QuotationChangeLogPageOrder orderV0) {
        PageResult<QuotationChangeLog> pageResult = quotationChangeLogService.getQuotationChangeLogPage(pageVO, orderV0);
        return success(quotationChangeLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报价变更日志 Excel")
    @PreAuthorize("@ss.hasPermission('jl:quotation-change-log:export')")
    @OperateLog(type = EXPORT)
    public void exportQuotationChangeLogExcel(@Valid QuotationChangeLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<QuotationChangeLog> list = quotationChangeLogService.getQuotationChangeLogList(exportReqVO);
        // 导出 Excel
        List<QuotationChangeLogExcelVO> excelData = quotationChangeLogMapper.toExcelList(list);
        ExcelUtils.write(response, "报价变更日志.xls", "数据", QuotationChangeLogExcelVO.class, excelData);
    }

}

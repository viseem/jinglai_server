package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication;

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

import cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo.*;
import cn.iocoder.yudao.module.jl.entity.invoiceapplication.InvoiceApplication;
import cn.iocoder.yudao.module.jl.mapper.invoiceapplication.InvoiceApplicationMapper;
import cn.iocoder.yudao.module.jl.service.invoiceapplication.InvoiceApplicationService;

@Tag(name = "管理后台 - 开票申请")
@RestController
@RequestMapping("/jl/invoice-application")
@Validated
public class InvoiceApplicationController {

    @Resource
    private InvoiceApplicationService invoiceApplicationService;

    @Resource
    private InvoiceApplicationMapper invoiceApplicationMapper;

    @PostMapping("/create")
    @Operation(summary = "创建开票申请")
    @PreAuthorize("@ss.hasPermission('jl:invoice-application:create')")
    public CommonResult<Long> createInvoiceApplication(@Valid @RequestBody InvoiceApplicationCreateReqVO createReqVO) {
        return success(invoiceApplicationService.createInvoiceApplication(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新开票申请")
    @PreAuthorize("@ss.hasPermission('jl:invoice-application:update')")
    public CommonResult<Boolean> updateInvoiceApplication(@Valid @RequestBody InvoiceApplicationUpdateReqVO updateReqVO) {
        invoiceApplicationService.updateInvoiceApplication(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新开票申请")
    @PreAuthorize("@ss.hasPermission('jl:invoice-application:update')")
    public CommonResult<Boolean> updateInvoiceApplicationStatus(@Valid @RequestBody InvoiceApplicationUpdateStatusReqVO updateReqVO) {
        invoiceApplicationService.updateInvoiceApplicationStatus(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除开票申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:invoice-application:delete')")
    public CommonResult<Boolean> deleteInvoiceApplication(@RequestParam("id") Long id) {
        invoiceApplicationService.deleteInvoiceApplication(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得开票申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:invoice-application:query')")
    public CommonResult<InvoiceApplicationRespVO> getInvoiceApplication(@RequestParam("id") Long id) {
            Optional<InvoiceApplication> invoiceApplication = invoiceApplicationService.getInvoiceApplication(id);
        return success(invoiceApplication.map(invoiceApplicationMapper::toDto).orElseThrow(() -> exception(INVOICE_APPLICATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得开票申请列表")
    @PreAuthorize("@ss.hasPermission('jl:invoice-application:query')")
    public CommonResult<PageResult<InvoiceApplicationRespVO>> getInvoiceApplicationPage(@Valid InvoiceApplicationPageReqVO pageVO, @Valid InvoiceApplicationPageOrder orderV0) {
        PageResult<InvoiceApplication> pageResult = invoiceApplicationService.getInvoiceApplicationPage(pageVO, orderV0);
        return success(invoiceApplicationMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出开票申请 Excel")
    @PreAuthorize("@ss.hasPermission('jl:invoice-application:export')")
    @OperateLog(type = EXPORT)
    public void exportInvoiceApplicationExcel(@Valid InvoiceApplicationExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InvoiceApplication> list = invoiceApplicationService.getInvoiceApplicationList(exportReqVO);
        // 导出 Excel
        List<InvoiceApplicationExcelVO> excelData = invoiceApplicationMapper.toExcelList(list);
        ExcelUtils.write(response, "开票申请.xls", "数据", InvoiceApplicationExcelVO.class, excelData);
    }

}

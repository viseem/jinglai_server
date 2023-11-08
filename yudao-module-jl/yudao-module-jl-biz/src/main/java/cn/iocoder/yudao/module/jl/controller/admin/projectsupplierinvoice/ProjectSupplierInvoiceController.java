package cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice.ProjectSupplierInvoice;
import cn.iocoder.yudao.module.jl.mapper.projectsupplierinvoice.ProjectSupplierInvoiceMapper;
import cn.iocoder.yudao.module.jl.service.projectsupplierinvoice.ProjectSupplierInvoiceService;

@Tag(name = "管理后台 - 采购供应商发票")
@RestController
@RequestMapping("/jl/project-supplier-invoice")
@Validated
public class ProjectSupplierInvoiceController {

    @Resource
    private ProjectSupplierInvoiceService projectSupplierInvoiceService;

    @Resource
    private ProjectSupplierInvoiceMapper projectSupplierInvoiceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建采购供应商发票")
    @PreAuthorize("@ss.hasPermission('jl:project-supplier-invoice:create')")
    public CommonResult<Long> createProjectSupplierInvoice(@Valid @RequestBody ProjectSupplierInvoiceCreateReqVO createReqVO) {
        return success(projectSupplierInvoiceService.createProjectSupplierInvoice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新采购供应商发票")
    @PreAuthorize("@ss.hasPermission('jl:project-supplier-invoice:update')")
    public CommonResult<Boolean> updateProjectSupplierInvoice(@Valid @RequestBody ProjectSupplierInvoiceUpdateReqVO updateReqVO) {
        projectSupplierInvoiceService.updateProjectSupplierInvoice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除采购供应商发票")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-supplier-invoice:delete')")
    public CommonResult<Boolean> deleteProjectSupplierInvoice(@RequestParam("id") Long id) {
        projectSupplierInvoiceService.deleteProjectSupplierInvoice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得采购供应商发票")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-supplier-invoice:query')")
    public CommonResult<ProjectSupplierInvoiceRespVO> getProjectSupplierInvoice(@RequestParam("id") Long id) {
            Optional<ProjectSupplierInvoice> projectSupplierInvoice = projectSupplierInvoiceService.getProjectSupplierInvoice(id);
        return success(projectSupplierInvoice.map(projectSupplierInvoiceMapper::toDto).orElseThrow(() -> exception(PROJECT_SUPPLIER_INVOICE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得采购供应商发票列表")
    @PreAuthorize("@ss.hasPermission('jl:project-supplier-invoice:query')")
    public CommonResult<PageResult<ProjectSupplierInvoiceRespVO>> getProjectSupplierInvoicePage(@Valid ProjectSupplierInvoicePageReqVO pageVO, @Valid ProjectSupplierInvoicePageOrder orderV0) {
        PageResult<ProjectSupplierInvoice> pageResult = projectSupplierInvoiceService.getProjectSupplierInvoicePage(pageVO, orderV0);
        return success(projectSupplierInvoiceMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出采购供应商发票 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-supplier-invoice:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectSupplierInvoiceExcel(@Valid ProjectSupplierInvoiceExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectSupplierInvoice> list = projectSupplierInvoiceService.getProjectSupplierInvoiceList(exportReqVO);
        // 导出 Excel
        List<ProjectSupplierInvoiceExcelVO> excelData = projectSupplierInvoiceMapper.toExcelList(list);
        ExcelUtils.write(response, "采购供应商发票.xls", "数据", ProjectSupplierInvoiceExcelVO.class, excelData);
    }

}

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
import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementPaymentMapper;
import cn.iocoder.yudao.module.jl.service.project.ProcurementPaymentService;

@Tag(name = "管理后台 - 项目采购单打款")
@RestController
@RequestMapping("/jl/procurement-payment")
@Validated
public class ProcurementPaymentController {

    @Resource
    private ProcurementPaymentService procurementPaymentService;

    @Resource
    private ProcurementPaymentMapper procurementPaymentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目采购单打款")
    @PreAuthorize("@ss.hasPermission('jl:procurement-payment:create')")
    public CommonResult<Long> createProcurementPayment(@Valid @RequestBody ProcurementPaymentCreateReqVO createReqVO) {
        return success(procurementPaymentService.createProcurementPayment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目采购单打款")
    @PreAuthorize("@ss.hasPermission('jl:procurement-payment:update')")
    public CommonResult<Boolean> updateProcurementPayment(@Valid @RequestBody ProcurementPaymentUpdateReqVO updateReqVO) {
        procurementPaymentService.updateProcurementPayment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目采购单打款")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:procurement-payment:delete')")
    public CommonResult<Boolean> deleteProcurementPayment(@RequestParam("id") Long id) {
        procurementPaymentService.deleteProcurementPayment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目采购单打款")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:procurement-payment:query')")
    public CommonResult<ProcurementPaymentRespVO> getProcurementPayment(@RequestParam("id") Long id) {
            Optional<ProcurementPayment> procurementPayment = procurementPaymentService.getProcurementPayment(id);
        return success(procurementPayment.map(procurementPaymentMapper::toDto).orElseThrow(() -> exception(PROCUREMENT_PAYMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目采购单打款列表")
    @PreAuthorize("@ss.hasPermission('jl:procurement-payment:query')")
    public CommonResult<PageResult<ProcurementPaymentRespVO>> getProcurementPaymentPage(@Valid ProcurementPaymentPageReqVO pageVO, @Valid ProcurementPaymentPageOrder orderV0) {
        PageResult<ProcurementPayment> pageResult = procurementPaymentService.getProcurementPaymentPage(pageVO, orderV0);
        return success(procurementPaymentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目采购单打款 Excel")
    @PreAuthorize("@ss.hasPermission('jl:procurement-payment:export')")
    @OperateLog(type = EXPORT)
    public void exportProcurementPaymentExcel(@Valid ProcurementPaymentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProcurementPayment> list = procurementPaymentService.getProcurementPaymentList(exportReqVO);
        // 导出 Excel
        List<ProcurementPaymentExcelVO> excelData = procurementPaymentMapper.toExcelList(list);
        ExcelUtils.write(response, "项目采购单打款.xls", "数据", ProcurementPaymentExcelVO.class, excelData);
    }

}

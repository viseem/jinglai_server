package cn.iocoder.yudao.module.jl.controller.admin.financepayment;

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

import cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo.*;
import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import cn.iocoder.yudao.module.jl.mapper.financepayment.FinancePaymentMapper;
import cn.iocoder.yudao.module.jl.service.financepayment.FinancePaymentService;

@Tag(name = "管理后台 - 财务打款")
@RestController
@RequestMapping("/jl/finance-payment")
@Validated
public class FinancePaymentController {

    @Resource
    private FinancePaymentService financePaymentService;

    @Resource
    private FinancePaymentMapper financePaymentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建财务打款")
    @PreAuthorize("@ss.hasPermission('jl:finance-payment:create')")
    public CommonResult<Long> createFinancePayment(@Valid @RequestBody FinancePaymentCreateReqVO createReqVO) {
        return success(financePaymentService.createFinancePayment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新财务打款")
    @PreAuthorize("@ss.hasPermission('jl:finance-payment:update')")
    public CommonResult<Boolean> updateFinancePayment(@Valid @RequestBody FinancePaymentUpdateReqVO updateReqVO) {
        financePaymentService.updateFinancePayment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除财务打款")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:finance-payment:delete')")
    public CommonResult<Boolean> deleteFinancePayment(@RequestParam("id") Long id) {
        financePaymentService.deleteFinancePayment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得财务打款")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:finance-payment:query')")
    public CommonResult<FinancePaymentRespVO> getFinancePayment(@RequestParam("id") Long id) {
            Optional<FinancePayment> financePayment = financePaymentService.getFinancePayment(id);
        return success(financePayment.map(financePaymentMapper::toDto).orElseThrow(() -> exception(FINANCE_PAYMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得财务打款列表")
    @PreAuthorize("@ss.hasPermission('jl:finance-payment:query')")
    public CommonResult<PageResult<FinancePaymentRespVO>> getFinancePaymentPage(@Valid FinancePaymentPageReqVO pageVO, @Valid FinancePaymentPageOrder orderV0) {
        PageResult<FinancePayment> pageResult = financePaymentService.getFinancePaymentPage(pageVO, orderV0);
        return success(financePaymentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出财务打款 Excel")
    @PreAuthorize("@ss.hasPermission('jl:finance-payment:export')")
    @OperateLog(type = EXPORT)
    public void exportFinancePaymentExcel(@Valid FinancePaymentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<FinancePayment> list = financePaymentService.getFinancePaymentList(exportReqVO);
        // 导出 Excel
        List<FinancePaymentExcelVO> excelData = financePaymentMapper.toExcelList(list);
        ExcelUtils.write(response, "财务打款.xls", "数据", FinancePaymentExcelVO.class, excelData);
    }

}

package cn.iocoder.yudao.module.jl.controller.admin.crm;

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

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceipt;
import cn.iocoder.yudao.module.jl.mapper.crm.CrmReceiptMapper;
import cn.iocoder.yudao.module.jl.service.crm.CrmReceiptService;

@Tag(name = "管理后台 - 客户发票")
@RestController
@RequestMapping("/jl/crm-receipt")
@Validated
public class CrmReceiptController {

    @Resource
    private CrmReceiptService crmReceiptService;

    @Resource
    private CrmReceiptMapper crmReceiptMapper;

    @PostMapping("/create")
    @Operation(summary = "创建客户发票")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt:create')")
    public CommonResult<Long> createCrmReceipt(@Valid @RequestBody CrmReceiptCreateReqVO createReqVO) {
        return success(crmReceiptService.createCrmReceipt(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户发票")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt:update')")
    public CommonResult<Boolean> updateCrmReceipt(@Valid @RequestBody CrmReceiptUpdateReqVO updateReqVO) {
        crmReceiptService.updateCrmReceipt(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除客户发票")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt:delete')")
    public CommonResult<Boolean> deleteCrmReceipt(@RequestParam("id") Long id) {
        crmReceiptService.deleteCrmReceipt(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得客户发票")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt:query')")
    public CommonResult<CrmReceiptRespVO> getCrmReceipt(@RequestParam("id") Long id) {
            Optional<CrmReceipt> crmReceipt = crmReceiptService.getCrmReceipt(id);
        return success(crmReceipt.map(crmReceiptMapper::toDto).orElseThrow(() -> exception(CRM_RECEIPT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得客户发票列表")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt:query')")
    public CommonResult<PageResult<CrmReceiptRespVO>> getCrmReceiptPage(@Valid CrmReceiptPageReqVO pageVO, @Valid CrmReceiptPageOrder orderV0) {
        PageResult<CrmReceipt> pageResult = crmReceiptService.getCrmReceiptPage(pageVO, orderV0);
        return success(crmReceiptMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户发票 Excel")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt:export')")
    @OperateLog(type = EXPORT)
    public void exportCrmReceiptExcel(@Valid CrmReceiptExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CrmReceipt> list = crmReceiptService.getCrmReceiptList(exportReqVO);
        // 导出 Excel
        List<CrmReceiptExcelVO> excelData = crmReceiptMapper.toExcelList(list);
        ExcelUtils.write(response, "客户发票.xls", "数据", CrmReceiptExcelVO.class, excelData);
    }

}

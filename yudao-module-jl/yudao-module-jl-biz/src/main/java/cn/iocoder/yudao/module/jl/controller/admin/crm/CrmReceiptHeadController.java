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
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceiptHead;
import cn.iocoder.yudao.module.jl.mapper.crm.CrmReceiptHeadMapper;
import cn.iocoder.yudao.module.jl.service.crm.CrmReceiptHeadService;

@Tag(name = "管理后台 - 客户发票抬头")
@RestController
@RequestMapping("/jl/crm-receipt-head")
@Validated
public class CrmReceiptHeadController {

    @Resource
    private CrmReceiptHeadService crmReceiptHeadService;

    @Resource
    private CrmReceiptHeadMapper crmReceiptHeadMapper;

    @PostMapping("/create")
    @Operation(summary = "创建客户发票抬头")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt-head:create')")
    public CommonResult<Long> createCrmReceiptHead(@Valid @RequestBody CrmReceiptHeadCreateReqVO createReqVO) {
        return success(crmReceiptHeadService.createCrmReceiptHead(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户发票抬头")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt-head:update')")
    public CommonResult<Boolean> updateCrmReceiptHead(@Valid @RequestBody CrmReceiptHeadUpdateReqVO updateReqVO) {
        crmReceiptHeadService.updateCrmReceiptHead(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除客户发票抬头")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt-head:delete')")
    public CommonResult<Boolean> deleteCrmReceiptHead(@RequestParam("id") Long id) {
        crmReceiptHeadService.deleteCrmReceiptHead(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得客户发票抬头")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt-head:query')")
    public CommonResult<CrmReceiptHeadRespVO> getCrmReceiptHead(@RequestParam("id") Long id) {
            Optional<CrmReceiptHead> crmReceiptHead = crmReceiptHeadService.getCrmReceiptHead(id);
        return success(crmReceiptHead.map(crmReceiptHeadMapper::toDto).orElseThrow(() -> exception(CRM_RECEIPT_HEAD_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得客户发票抬头列表")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt-head:query')")
    public CommonResult<PageResult<CrmReceiptHeadRespVO>> getCrmReceiptHeadPage(@Valid CrmReceiptHeadPageReqVO pageVO, @Valid CrmReceiptHeadPageOrder orderV0) {
        PageResult<CrmReceiptHead> pageResult = crmReceiptHeadService.getCrmReceiptHeadPage(pageVO, orderV0);
        return success(crmReceiptHeadMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户发票抬头 Excel")
    @PreAuthorize("@ss.hasPermission('jl:crm-receipt-head:export')")
    @OperateLog(type = EXPORT)
    public void exportCrmReceiptHeadExcel(@Valid CrmReceiptHeadExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CrmReceiptHead> list = crmReceiptHeadService.getCrmReceiptHeadList(exportReqVO);
        // 导出 Excel
        List<CrmReceiptHeadExcelVO> excelData = crmReceiptHeadMapper.toExcelList(list);
        ExcelUtils.write(response, "客户发票抬头.xls", "数据", CrmReceiptHeadExcelVO.class, excelData);
    }

}

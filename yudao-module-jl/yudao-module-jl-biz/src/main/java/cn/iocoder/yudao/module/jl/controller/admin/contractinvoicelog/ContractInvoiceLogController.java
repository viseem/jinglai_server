package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog;

import cn.iocoder.yudao.framework.excel.core.util.excelhandler.CustomExcelCellSizeWriterHandler;
import cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo.ContractFundLogImportRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo.ContractFundLogImportVO;
import io.swagger.v3.oas.annotations.Parameters;
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

import cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.mapper.contractinvoicelog.ContractInvoiceLogMapper;
import cn.iocoder.yudao.module.jl.service.contractinvoicelog.ContractInvoiceLogService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 合同发票记录")
@RestController
@RequestMapping("/jl/contract-invoice-log")
@Validated
public class ContractInvoiceLogController {

    @Resource
    private ContractInvoiceLogService contractInvoiceLogService;

    @Resource
    private ContractInvoiceLogMapper contractInvoiceLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建合同发票记录")
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:create')")
    public CommonResult<Long> createContractInvoiceLog(@Valid @RequestBody ContractInvoiceLogCreateReqVO createReqVO) {
        return success(contractInvoiceLogService.createContractInvoiceLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同发票记录")
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:update')")
    public CommonResult<Boolean> updateContractInvoiceLog(@Valid @RequestBody ContractInvoiceLogUpdateReqVO updateReqVO) {
        contractInvoiceLogService.updateContractInvoiceLog(updateReqVO);
        return success(true);
    }

    @PutMapping("/void-apply")
    @Operation(summary = "申请作废发票")
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:update')")
    public CommonResult<Boolean> applyVoidContractInvoiceLog(@Valid @RequestBody ContractInvoiceLogVoidReqVO vo) {
        contractInvoiceLogService.applyVoidContractInvoiceLog(vo);
        return success(true);
    }

    @PutMapping("/void-audit")
    @Operation(summary = "审批作废发票")
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:update')")
    public CommonResult<Boolean> auditVoidContractInvoiceLog(@Valid @RequestBody ContractInvoiceLogVoidReqVO vo) {
        contractInvoiceLogService.auditVoidContractInvoiceLog(vo);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除合同发票记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:delete')")
    public CommonResult<Boolean> deleteContractInvoiceLog(@RequestParam("id") Long id) {
        contractInvoiceLogService.deleteContractInvoiceLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得合同发票记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:query')")
    public CommonResult<ContractInvoiceLogRespVO> getContractInvoiceLog(@RequestParam("id") Long id) {
            Optional<ContractInvoiceLog> contractInvoiceLog = contractInvoiceLogService.getContractInvoiceLog(id);
        return success(contractInvoiceLog.map(contractInvoiceLogMapper::toDto).orElseThrow(() -> exception(CONTRACT_INVOICE_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得合同发票记录列表")
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:query')")
    public CommonResult<PageResult<ContractInvoiceLogRespVO>> getContractInvoiceLogPage(@Valid ContractInvoiceLogPageReqVO pageVO, @Valid ContractInvoiceLogPageOrder orderV0) {
        PageResult<ContractInvoiceLog> pageResult = contractInvoiceLogService.getContractInvoiceLogPage(pageVO, orderV0);
        return success(contractInvoiceLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同发票记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:contract-invoice-log:export')")
    @OperateLog(type = EXPORT)
    public void exportContractInvoiceLogExcel(@Valid ContractInvoiceLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ContractInvoiceLog> list = contractInvoiceLogService.getContractInvoiceLogList(exportReqVO);
        // 导出 Excel
        List<ContractInvoiceLogExcelVO> excelData = contractInvoiceLogMapper.toExcelList(list);
        Map<Integer, Integer> columnWidthMap = new HashMap<>();
//        columnWidthMap.put(2, 23);
        ExcelUtils.write(response, "合同发票记录.xls", "数据", ContractInvoiceLogExcelVO.class, excelData,new CustomExcelCellSizeWriterHandler(columnWidthMap));
    }

    //excel导入
    @PostMapping("/import-excel")
    @Operation(summary = "导入发票")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('system:invoice-log:import')")
    public CommonResult<ContractInvoiceLogImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                                 @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<ContractInvoiceLogImportVO> list = ExcelUtils.read(file, ContractInvoiceLogImportVO.class);
        return success(contractInvoiceLogService.importList(list, updateSupport));
    }


}

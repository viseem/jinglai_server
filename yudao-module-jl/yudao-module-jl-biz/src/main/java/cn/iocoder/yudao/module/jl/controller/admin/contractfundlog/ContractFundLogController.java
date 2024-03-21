package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplierImportRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplierImportVO;
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

import cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.mapper.contractfundlog.ContractFundLogMapper;
import cn.iocoder.yudao.module.jl.service.contractfundlog.ContractFundLogService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 合同收款记录")
@RestController
@RequestMapping("/jl/contract-fund-log")
@Validated
public class ContractFundLogController {

    @Resource
    private ContractFundLogService contractFundLogService;

    @Resource
    private ContractFundLogMapper contractFundLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建合同收款记录")
    @PreAuthorize("@ss.hasPermission('jl:contract-fund-log:create')")
    public CommonResult<Long> createContractFundLog(@Valid @RequestBody ContractFundLogCreateReqVO createReqVO) {
        return success(contractFundLogService.createContractFundLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同收款记录")
    @PreAuthorize("@ss.hasPermission('jl:contract-fund-log:update')")
    public CommonResult<Boolean> updateContractFundLog(@Valid @RequestBody ContractFundLogUpdateReqVO updateReqVO) {
        contractFundLogService.updateContractFundLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除合同收款记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:contract-fund-log:delete')")
    public CommonResult<Boolean> deleteContractFundLog(@RequestParam("id") Long id) {
        contractFundLogService.deleteContractFundLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得合同收款记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:contract-fund-log:query')")
    public CommonResult<ContractFundLogRespVO> getContractFundLog(@RequestParam("id") Long id) {
            Optional<ContractFundLog> contractFundLog = contractFundLogService.getContractFundLog(id);
        return success(contractFundLog.map(contractFundLogMapper::toDto).orElseThrow(() -> exception(CONTRACT_FUND_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得合同收款记录列表")
    @PreAuthorize("@ss.hasPermission('jl:contract-fund-log:query')")
    public CommonResult<PageResult<ContractFundLogRespVO>> getContractFundLogPage(@Valid ContractFundLogPageReqVO pageVO, @Valid ContractFundLogPageOrder orderV0) {
        PageResult<ContractFundLog> pageResult = contractFundLogService.getContractFundLogPage(pageVO, orderV0);
        return success(contractFundLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同收款记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:contract-fund-log:export')")
    @OperateLog(type = EXPORT)
    public void exportContractFundLogExcel(@Valid ContractFundLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ContractFundLog> list = contractFundLogService.getContractFundLogList(exportReqVO);
        // 导出 Excel
        List<ContractFundLogExcelVO> excelData = contractFundLogMapper.toExcelList(list);
        ExcelUtils.write(response, "合同收款记录.xls", "数据", ContractFundLogExcelVO.class, excelData);
    }

    //excel导入
    @PostMapping("/import-excel")
    @Operation(summary = "导入用户")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public CommonResult<ContractFundLogImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                          @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<ContractFundLogImportVO> list = ExcelUtils.read(file, ContractFundLogImportVO.class);
        return success(contractFundLogService.importList(list, updateSupport));
    }

}

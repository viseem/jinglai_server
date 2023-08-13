package cn.iocoder.yudao.module.jl.controller.admin.contract;

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

import cn.iocoder.yudao.module.jl.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.jl.entity.contract.ContractApproval;
import cn.iocoder.yudao.module.jl.mapper.contract.ContractApprovalMapper;
import cn.iocoder.yudao.module.jl.service.contract.ContractApprovalService;

@Tag(name = "管理后台 - 合同状态变更记录")
@RestController
@RequestMapping("/jl/contract-approval")
@Validated
public class ContractApprovalController {

    @Resource
    private ContractApprovalService contractApprovalService;

    @Resource
    private ContractApprovalMapper contractApprovalMapper;

    @PostMapping("/create")
    @Operation(summary = "创建合同状态变更记录")
    @PreAuthorize("@ss.hasPermission('jl:contract-approval:create')")
    public CommonResult<Long> createContractApproval(@Valid @RequestBody ContractApprovalCreateReqVO createReqVO) {
        return success(contractApprovalService.createContractApproval(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同状态变更记录")
    @PreAuthorize("@ss.hasPermission('jl:contract-approval:update')")
    public CommonResult<Boolean> updateContractApproval(@Valid @RequestBody ContractApprovalUpdateReqVO updateReqVO) {
        contractApprovalService.updateContractApproval(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除合同状态变更记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:contract-approval:delete')")
    public CommonResult<Boolean> deleteContractApproval(@RequestParam("id") Long id) {
        contractApprovalService.deleteContractApproval(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得合同状态变更记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:contract-approval:query')")
    public CommonResult<ContractApprovalRespVO> getContractApproval(@RequestParam("id") Long id) {
            Optional<ContractApproval> contractApproval = contractApprovalService.getContractApproval(id);
        return success(contractApproval.map(contractApprovalMapper::toDto).orElseThrow(() -> exception(CONTRACT_APPROVAL_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得合同状态变更记录列表")
    @PreAuthorize("@ss.hasPermission('jl:contract-approval:query')")
    public CommonResult<PageResult<ContractApprovalRespVO>> getContractApprovalPage(@Valid ContractApprovalPageReqVO pageVO, @Valid ContractApprovalPageOrder orderV0) {
        PageResult<ContractApproval> pageResult = contractApprovalService.getContractApprovalPage(pageVO, orderV0);
        return success(contractApprovalMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同状态变更记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:contract-approval:export')")
    @OperateLog(type = EXPORT)
    public void exportContractApprovalExcel(@Valid ContractApprovalExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ContractApproval> list = contractApprovalService.getContractApprovalList(exportReqVO);
        // 导出 Excel
        List<ContractApprovalExcelVO> excelData = contractApprovalMapper.toExcelList(list);
        ExcelUtils.write(response, "合同状态变更记录.xls", "数据", ContractApprovalExcelVO.class, excelData);
    }

}

package cn.iocoder.yudao.module.jl.controller.admin.purchasecontract;

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

import cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo.*;
import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContract;
import cn.iocoder.yudao.module.jl.mapper.purchasecontract.PurchaseContractMapper;
import cn.iocoder.yudao.module.jl.service.purchasecontract.PurchaseContractService;

@Tag(name = "管理后台 - 购销合同")
@RestController
@RequestMapping("/jl/purchase-contract")
@Validated
public class PurchaseContractController {

    @Resource
    private PurchaseContractService purchaseContractService;

    @Resource
    private PurchaseContractMapper purchaseContractMapper;

    @PostMapping("/create")
    @Operation(summary = "创建购销合同")
    @PreAuthorize("@ss.hasPermission('jl:purchase-contract:create')")
    public CommonResult<Long> createPurchaseContract(@Valid @RequestBody PurchaseContractCreateReqVO createReqVO) {
        return success(purchaseContractService.createPurchaseContract(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新购销合同")
    @PreAuthorize("@ss.hasPermission('jl:purchase-contract:update')")
    public CommonResult<Boolean> updatePurchaseContract(@Valid @RequestBody PurchaseContractUpdateReqVO updateReqVO) {
        purchaseContractService.updatePurchaseContract(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除购销合同")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:purchase-contract:delete')")
    public CommonResult<Boolean> deletePurchaseContract(@RequestParam("id") Long id) {
        purchaseContractService.deletePurchaseContract(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得购销合同")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:purchase-contract:query')")
    public CommonResult<PurchaseContractRespVO> getPurchaseContract(@RequestParam("id") Long id) {
            Optional<PurchaseContract> purchaseContract = purchaseContractService.getPurchaseContract(id);
        return success(purchaseContract.map(purchaseContractMapper::toDto).orElseThrow(() -> exception(PURCHASE_CONTRACT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得购销合同列表")
    @PreAuthorize("@ss.hasPermission('jl:purchase-contract:query')")
    public CommonResult<PageResult<PurchaseContractRespVO>> getPurchaseContractPage(@Valid PurchaseContractPageReqVO pageVO, @Valid PurchaseContractPageOrder orderV0) {
        PageResult<PurchaseContract> pageResult = purchaseContractService.getPurchaseContractPage(pageVO, orderV0);
        return success(purchaseContractMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出购销合同 Excel")
    @PreAuthorize("@ss.hasPermission('jl:purchase-contract:export')")
    @OperateLog(type = EXPORT)
    public void exportPurchaseContractExcel(@Valid PurchaseContractExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<PurchaseContract> list = purchaseContractService.getPurchaseContractList(exportReqVO);
        // 导出 Excel
        List<PurchaseContractExcelVO> excelData = purchaseContractMapper.toExcelList(list);
        ExcelUtils.write(response, "购销合同.xls", "数据", PurchaseContractExcelVO.class, excelData);
    }

}

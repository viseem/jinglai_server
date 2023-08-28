package cn.iocoder.yudao.module.jl.controller.admin.project;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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
import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementMapper;
import cn.iocoder.yudao.module.jl.service.project.ProcurementService;

@Tag(name = "管理后台 - 项目采购")
@RestController
@RequestMapping("/jl/procurement")
@Validated
public class ProcurementController {

    @Resource
    private ProcurementService procurementService;

    @Resource
    private ProcurementMapper procurementMapper;

//    @PostMapping("/create")
//    @Operation(summary = "创建项目采购单申请")
//    @PreAuthorize("@ss.hasPermission('jl:procurement:create')")
//    public CommonResult<Long> createProcurement(@Valid @RequestBody ProcurementCreateReqVO createReqVO) {
//        return success(procurementService.createProcurement(createReqVO));
//    }
//
    @PutMapping("/update")
    @Operation(summary = "更新项目采购单申请")
    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
    public CommonResult<Boolean> updateProcurement(@Valid @RequestBody ProcurementUpdateReqVO updateReqVO) {
        procurementService.updateProcurement(updateReqVO);
        return success(true);
    }

    @PutMapping("/save")
    @Operation(summary = "全量保存项目采购单申请")
    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
    public CommonResult<Boolean> saveProcurement(@Valid @RequestBody ProcurementSaveReqVO saveReqVO) {
        procurementService.saveProcurement(saveReqVO);
        return success(true);
    }

    @PutMapping("/update-shipment")
    @Operation(summary = "更新物流信息")
    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
    public CommonResult<Boolean> updateShipmentOfProcurement(@RequestBody ProcurementUpdateShipmentsReqVO saveReqVO) {
        procurementService.saveShipments(saveReqVO);
        return success(true);
    }

    @PutMapping("/update-payment")
    @Operation(summary = "更新打款信息")
    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
    public CommonResult<Boolean> updatePaymentOfProcurement(@Valid @RequestBody ProcurementUpdatePaymentsReqVO saveReqVO) {
        procurementService.savePayments(saveReqVO);
        return success(true);
    }

    @PutMapping("/check-in")
    @Operation(summary = "签收物品")
    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
    public CommonResult<Boolean> checkInShipmentProcurement(@Valid @RequestBody ProcurementShipmentCheckInReqVO saveReqVO) {
        procurementService.checkIn(saveReqVO);
        return success(true);
    }

    @PutMapping("/store-in")
    @Operation(summary = "签收物品")
    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
    public CommonResult<Boolean> storeProcurementItem(@Valid @RequestBody StoreInProcurementItemReqVO saveReqVO) {
        procurementService.storeIn(saveReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目采购单申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:procurement:delete')")
    public CommonResult<Boolean> deleteProcurement(@RequestParam("id") Long id) {
        procurementService.deleteProcurement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目采购单申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:procurement:query')")
    public CommonResult<ProcurementRespVO> getProcurement(@RequestParam("id") Long id) {
            Optional<Procurement> procurement = procurementService.getProcurement(id);
        return success(procurement.map(procurementMapper::toDto).orElseThrow(() -> exception(PROCUREMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目采购单申请列表")
    @PreAuthorize("@ss.hasPermission('jl:procurement:query')")
    public CommonResult<PageResult<ProcurementRespVO>> getProcurementPage(@Valid ProcurementPageReqVO pageVO, @Valid ProcurementPageOrder orderV0) {
        PageResult<Procurement> pageResult = procurementService.getProcurementPage(pageVO, orderV0);
        return success(procurementMapper.toPage(pageResult));
    }

    @GetMapping("/stats")
    @Operation(summary = "(分页)获得项目采购单申请列表")
    @PreAuthorize("@ss.hasPermission('jl:procurement:query')")
    public CommonResult<ProcurementStatsRespVO> getProcurementPage(@Valid ProcurementPageReqVO pageVO) {
        ProcurementStatsRespVO procurementStatsRespVO = procurementService.getProcurementStats(pageVO);
        return success(procurementStatsRespVO);
    }

    @GetMapping("/paid-page")
    @Operation(summary = "(分页)获得项目采购单申请列表")
    @PreAuthorize("@ss.hasPermission('jl:procurement:query')")
    public CommonResult<PageResult<ProcurementRespVO>> getProcurementPaidPage(@Valid ProcurementPageReqVO pageVO, @Valid ProcurementPageOrder orderV0) {
        PageResult<Procurement> pageResult = procurementService.getProcurementPaidPage(pageVO, orderV0);
        // 转换格式
        return success(procurementMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目采购单申请 Excel")
    @PreAuthorize("@ss.hasPermission('jl:procurement:export')")
    @OperateLog(type = EXPORT)
    public void exportProcurementExcel(@Valid ProcurementExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Procurement> list = procurementService.getProcurementList(exportReqVO);
        // 导出 Excel
        List<ProcurementExcelVO> excelData = procurementMapper.toExcelList(list);
        ExcelUtils.write(response, "项目采购单申请.xls", "数据", ProcurementExcelVO.class, excelData);
    }

}

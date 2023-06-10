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
import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementShipmentMapper;
import cn.iocoder.yudao.module.jl.service.project.ProcurementShipmentService;

@Tag(name = "管理后台 - 项目采购单物流信息")
@RestController
@RequestMapping("/jl/procurement-shipment")
@Validated
public class ProcurementShipmentController {

    @Resource
    private ProcurementShipmentService procurementShipmentService;

    @Resource
    private ProcurementShipmentMapper procurementShipmentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目采购单物流信息")
    @PreAuthorize("@ss.hasPermission('jl:procurement-shipment:create')")
    public CommonResult<Long> createProcurementShipment(@Valid @RequestBody ProcurementShipmentCreateReqVO createReqVO) {
        return success(procurementShipmentService.createProcurementShipment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目采购单物流信息")
    @PreAuthorize("@ss.hasPermission('jl:procurement-shipment:update')")
    public CommonResult<Boolean> updateProcurementShipment(@Valid @RequestBody ProcurementShipmentUpdateReqVO updateReqVO) {
        procurementShipmentService.updateProcurementShipment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目采购单物流信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:procurement-shipment:delete')")
    public CommonResult<Boolean> deleteProcurementShipment(@RequestParam("id") Long id) {
        procurementShipmentService.deleteProcurementShipment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目采购单物流信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:procurement-shipment:query')")
    public CommonResult<ProcurementShipmentRespVO> getProcurementShipment(@RequestParam("id") Long id) {
            Optional<ProcurementShipment> procurementShipment = procurementShipmentService.getProcurementShipment(id);
        return success(procurementShipment.map(procurementShipmentMapper::toDto).orElseThrow(() -> exception(PROCUREMENT_SHIPMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目采购单物流信息列表")
    @PreAuthorize("@ss.hasPermission('jl:procurement-shipment:query')")
    public CommonResult<PageResult<ProcurementShipmentRespVO>> getProcurementShipmentPage(@Valid ProcurementShipmentPageReqVO pageVO, @Valid ProcurementShipmentPageOrder orderV0) {
        PageResult<ProcurementShipment> pageResult = procurementShipmentService.getProcurementShipmentPage(pageVO, orderV0);
        return success(procurementShipmentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目采购单物流信息 Excel")
    @PreAuthorize("@ss.hasPermission('jl:procurement-shipment:export')")
    @OperateLog(type = EXPORT)
    public void exportProcurementShipmentExcel(@Valid ProcurementShipmentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProcurementShipment> list = procurementShipmentService.getProcurementShipmentList(exportReqVO);
        // 导出 Excel
        List<ProcurementShipmentExcelVO> excelData = procurementShipmentMapper.toExcelList(list);
        ExcelUtils.write(response, "项目采购单物流信息.xls", "数据", ProcurementShipmentExcelVO.class, excelData);
    }

}

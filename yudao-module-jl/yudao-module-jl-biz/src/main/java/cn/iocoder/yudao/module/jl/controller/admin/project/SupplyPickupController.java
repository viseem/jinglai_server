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
import cn.iocoder.yudao.module.jl.entity.project.SupplyPickup;
import cn.iocoder.yudao.module.jl.mapper.project.SupplyPickupMapper;
import cn.iocoder.yudao.module.jl.service.project.SupplyPickupService;

@Tag(name = "管理后台 - 取货单申请")
@RestController
@RequestMapping("/jl/supply-pickup")
@Validated
public class SupplyPickupController {

    @Resource
    private SupplyPickupService supplyPickupService;

    @Resource
    private SupplyPickupMapper supplyPickupMapper;

    @PostMapping("/create")
    @Operation(summary = "创建取货单申请")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup:create')")
    public CommonResult<Long> createSupplyPickup(@Valid @RequestBody SupplyPickupCreateReqVO createReqVO) {
        return success(supplyPickupService.createSupplyPickup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新取货单申请")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup:update')")
    public CommonResult<Boolean> updateSupplyPickup(@Valid @RequestBody SupplyPickupUpdateReqVO updateReqVO) {
        supplyPickupService.updateSupplyPickup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除取货单申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup:delete')")
    public CommonResult<Boolean> deleteSupplyPickup(@RequestParam("id") Long id) {
        supplyPickupService.deleteSupplyPickup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得取货单申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup:query')")
    public CommonResult<SupplyPickupRespVO> getSupplyPickup(@RequestParam("id") Long id) {
            Optional<SupplyPickup> supplyPickup = supplyPickupService.getSupplyPickup(id);
        return success(supplyPickup.map(supplyPickupMapper::toDto).orElseThrow(() -> exception(SUPPLY_PICKUP_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得取货单申请列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup:query')")
    public CommonResult<PageResult<SupplyPickupRespVO>> getSupplyPickupPage(@Valid SupplyPickupPageReqVO pageVO, @Valid SupplyPickupPageOrder orderV0) {
        PageResult<SupplyPickup> pageResult = supplyPickupService.getSupplyPickupPage(pageVO, orderV0);
        return success(supplyPickupMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出取货单申请 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplyPickupExcel(@Valid SupplyPickupExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplyPickup> list = supplyPickupService.getSupplyPickupList(exportReqVO);
        // 导出 Excel
        List<SupplyPickupExcelVO> excelData = supplyPickupMapper.toExcelList(list);
        ExcelUtils.write(response, "取货单申请.xls", "数据", SupplyPickupExcelVO.class, excelData);
    }

}

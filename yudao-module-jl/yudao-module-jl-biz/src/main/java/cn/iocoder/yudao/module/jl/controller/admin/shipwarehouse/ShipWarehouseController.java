package cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse;

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

import cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo.*;
import cn.iocoder.yudao.module.jl.entity.shipwarehouse.ShipWarehouse;
import cn.iocoder.yudao.module.jl.mapper.shipwarehouse.ShipWarehouseMapper;
import cn.iocoder.yudao.module.jl.service.shipwarehouse.ShipWarehouseService;

@Tag(name = "管理后台 - 收货仓库")
@RestController
@RequestMapping("/jl/ship-warehouse")
@Validated
public class ShipWarehouseController {

    @Resource
    private ShipWarehouseService shipWarehouseService;

    @Resource
    private ShipWarehouseMapper shipWarehouseMapper;

    @PostMapping("/create")
    @Operation(summary = "创建收货仓库")
    @PreAuthorize("@ss.hasPermission('jl:ship-warehouse:create')")
    public CommonResult<Long> createShipWarehouse(@Valid @RequestBody ShipWarehouseCreateReqVO createReqVO) {
        return success(shipWarehouseService.createShipWarehouse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收货仓库")
    @PreAuthorize("@ss.hasPermission('jl:ship-warehouse:update')")
    public CommonResult<Boolean> updateShipWarehouse(@Valid @RequestBody ShipWarehouseUpdateReqVO updateReqVO) {
        shipWarehouseService.updateShipWarehouse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除收货仓库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:ship-warehouse:delete')")
    public CommonResult<Boolean> deleteShipWarehouse(@RequestParam("id") Long id) {
        shipWarehouseService.deleteShipWarehouse(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得收货仓库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:ship-warehouse:query')")
    public CommonResult<ShipWarehouseRespVO> getShipWarehouse(@RequestParam("id") Long id) {
            Optional<ShipWarehouse> shipWarehouse = shipWarehouseService.getShipWarehouse(id);
        return success(shipWarehouse.map(shipWarehouseMapper::toDto).orElseThrow(() -> exception(SHIP_WAREHOUSE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得收货仓库列表")
    @PreAuthorize("@ss.hasPermission('jl:ship-warehouse:query')")
    public CommonResult<PageResult<ShipWarehouseRespVO>> getShipWarehousePage(@Valid ShipWarehousePageReqVO pageVO, @Valid ShipWarehousePageOrder orderV0) {
        PageResult<ShipWarehouse> pageResult = shipWarehouseService.getShipWarehousePage(pageVO, orderV0);
        return success(shipWarehouseMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收货仓库 Excel")
    @PreAuthorize("@ss.hasPermission('jl:ship-warehouse:export')")
    @OperateLog(type = EXPORT)
    public void exportShipWarehouseExcel(@Valid ShipWarehouseExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ShipWarehouse> list = shipWarehouseService.getShipWarehouseList(exportReqVO);
        // 导出 Excel
        List<ShipWarehouseExcelVO> excelData = shipWarehouseMapper.toExcelList(list);
        ExcelUtils.write(response, "收货仓库.xls", "数据", ShipWarehouseExcelVO.class, excelData);
    }

}

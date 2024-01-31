package cn.iocoder.yudao.module.jl.controller.admin.inventory;

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

import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainerPlace;
import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryContainerPlaceMapper;
import cn.iocoder.yudao.module.jl.service.inventory.InventoryContainerPlaceService;

@Tag(name = "管理后台 - 库管存储容器位置")
@RestController
@RequestMapping("/jl/inventory-container-place")
@Validated
public class InventoryContainerPlaceController {

    @Resource
    private InventoryContainerPlaceService inventoryContainerPlaceService;

    @Resource
    private InventoryContainerPlaceMapper inventoryContainerPlaceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建库管存储容器位置")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container-place:create')")
    public CommonResult<Long> createInventoryContainerPlace(@Valid @RequestBody InventoryContainerPlaceCreateReqVO createReqVO) {
        return success(inventoryContainerPlaceService.createInventoryContainerPlace(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库管存储容器位置")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container-place:update')")
    public CommonResult<Boolean> updateInventoryContainerPlace(@Valid @RequestBody InventoryContainerPlaceUpdateReqVO updateReqVO) {
        inventoryContainerPlaceService.updateInventoryContainerPlace(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除库管存储容器位置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-container-place:delete')")
    public CommonResult<Boolean> deleteInventoryContainerPlace(@RequestParam("id") Long id) {
        inventoryContainerPlaceService.deleteInventoryContainerPlace(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得库管存储容器位置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container-place:query')")
    public CommonResult<InventoryContainerPlaceRespVO> getInventoryContainerPlace(@RequestParam("id") Long id) {
            Optional<InventoryContainerPlace> inventoryContainerPlace = inventoryContainerPlaceService.getInventoryContainerPlace(id);
        return success(inventoryContainerPlace.map(inventoryContainerPlaceMapper::toDto).orElseThrow(() -> exception(INVENTORY_CONTAINER_PLACE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得库管存储容器位置列表")
    public CommonResult<PageResult<InventoryContainerPlaceRespVO>> getInventoryContainerPlacePage(@Valid InventoryContainerPlacePageReqVO pageVO, @Valid InventoryContainerPlacePageOrder orderV0) {
        PageResult<InventoryContainerPlace> pageResult = inventoryContainerPlaceService.getInventoryContainerPlacePage(pageVO, orderV0);
        return success(inventoryContainerPlaceMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库管存储容器位置 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container-place:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryContainerPlaceExcel(@Valid InventoryContainerPlaceExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryContainerPlace> list = inventoryContainerPlaceService.getInventoryContainerPlaceList(exportReqVO);
        // 导出 Excel
        List<InventoryContainerPlaceExcelVO> excelData = inventoryContainerPlaceMapper.toExcelList(list);
        ExcelUtils.write(response, "库管存储容器位置.xls", "数据", InventoryContainerPlaceExcelVO.class, excelData);
    }

}

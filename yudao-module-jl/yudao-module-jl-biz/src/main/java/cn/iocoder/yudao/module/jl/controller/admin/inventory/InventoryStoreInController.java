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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryStoreInMapper;
import cn.iocoder.yudao.module.jl.service.inventory.InventoryStoreInService;

@Tag(name = "管理后台 - 入库记录")
@RestController
@RequestMapping("/jl/inventory-store-in")
@Validated
public class InventoryStoreInController {

    @Resource
    private InventoryStoreInService inventoryStoreInService;

    @Resource
    private InventoryStoreInMapper inventoryStoreInMapper;

    @PostMapping("/create")
    @Operation(summary = "创建入库记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-in:create')")
    public CommonResult<Long> createInventoryStoreIn(@Valid @RequestBody InventoryStoreInCreateReqVO createReqVO) {
        return success(inventoryStoreInService.createInventoryStoreIn(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新入库记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-in:update')")
    public CommonResult<Boolean> updateInventoryStoreIn(@Valid @RequestBody InventoryStoreInUpdateReqVO updateReqVO) {
        inventoryStoreInService.updateInventoryStoreIn(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除入库记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-in:delete')")
    public CommonResult<Boolean> deleteInventoryStoreIn(@RequestParam("id") Long id) {
        inventoryStoreInService.deleteInventoryStoreIn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得入库记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-in:query')")
    public CommonResult<InventoryStoreInRespVO> getInventoryStoreIn(@RequestParam("id") Long id) {
            Optional<InventoryStoreIn> inventoryStoreIn = inventoryStoreInService.getInventoryStoreIn(id);
        return success(inventoryStoreIn.map(inventoryStoreInMapper::toDto).orElseThrow(() -> exception(INVENTORY_STORE_IN_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得入库记录列表")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-in:query')")
    public CommonResult<PageResult<InventoryStoreInRespVO>> getInventoryStoreInPage(@Valid InventoryStoreInPageReqVO pageVO, @Valid InventoryStoreInPageOrder orderV0) {
        PageResult<InventoryStoreIn> pageResult = inventoryStoreInService.getInventoryStoreInPage(pageVO, orderV0);
        return success(inventoryStoreInMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出入库记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-in:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryStoreInExcel(@Valid InventoryStoreInExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryStoreIn> list = inventoryStoreInService.getInventoryStoreInList(exportReqVO);
        // 导出 Excel
        List<InventoryStoreInExcelVO> excelData = inventoryStoreInMapper.toExcelList(list);
        ExcelUtils.write(response, "入库记录.xls", "数据", InventoryStoreInExcelVO.class, excelData);
    }

}

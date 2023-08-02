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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryStoreOutMapper;
import cn.iocoder.yudao.module.jl.service.inventory.InventoryStoreOutService;

@Tag(name = "管理后台 - 出库记录")
@RestController
@RequestMapping("/jl/inventory-store-out")
@Validated
public class InventoryStoreOutController {

    @Resource
    private InventoryStoreOutService inventoryStoreOutService;

    @Resource
    private InventoryStoreOutMapper inventoryStoreOutMapper;

    @PostMapping("/create")
    @Operation(summary = "创建出库记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-out:create')")
    public CommonResult<Long> createInventoryStoreOut(@Valid @RequestBody InventoryStoreOutCreateReqVO createReqVO) {
        return success(inventoryStoreOutService.createInventoryStoreOut(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新出库记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-out:update')")
    public CommonResult<Boolean> updateInventoryStoreOut(@Valid @RequestBody InventoryStoreOutUpdateReqVO updateReqVO) {
        inventoryStoreOutService.updateInventoryStoreOut(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除出库记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-out:delete')")
    public CommonResult<Boolean> deleteInventoryStoreOut(@RequestParam("id") Long id) {
        inventoryStoreOutService.deleteInventoryStoreOut(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得出库记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-out:query')")
    public CommonResult<InventoryStoreOutRespVO> getInventoryStoreOut(@RequestParam("id") Long id) {
            Optional<InventoryStoreOut> inventoryStoreOut = inventoryStoreOutService.getInventoryStoreOut(id);
        return success(inventoryStoreOut.map(inventoryStoreOutMapper::toDto).orElseThrow(() -> exception(INVENTORY_STORE_OUT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得出库记录列表")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-out:query')")
    public CommonResult<PageResult<InventoryStoreOutRespVO>> getInventoryStoreOutPage(@Valid InventoryStoreOutPageReqVO pageVO, @Valid InventoryStoreOutPageOrder orderV0) {
        PageResult<InventoryStoreOut> pageResult = inventoryStoreOutService.getInventoryStoreOutPage(pageVO, orderV0);
        return success(inventoryStoreOutMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出出库记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-out:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryStoreOutExcel(@Valid InventoryStoreOutExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryStoreOut> list = inventoryStoreOutService.getInventoryStoreOutList(exportReqVO);
        // 导出 Excel
        List<InventoryStoreOutExcelVO> excelData = inventoryStoreOutMapper.toExcelList(list);
        ExcelUtils.write(response, "出库记录.xls", "数据", InventoryStoreOutExcelVO.class, excelData);
    }

}

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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryCheckInMapper;
import cn.iocoder.yudao.module.jl.service.inventory.InventoryCheckInService;

@Tag(name = "管理后台 - 签收记录")
@RestController
@RequestMapping("/jl/inventory-check-in")
@Validated
public class InventoryCheckInController {

    @Resource
    private InventoryCheckInService inventoryCheckInService;

    @Resource
    private InventoryCheckInMapper inventoryCheckInMapper;

    @PostMapping("/create")
    @Operation(summary = "创建签收记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-check-in:create')")
    public CommonResult<Long> createInventoryCheckIn(@Valid @RequestBody InventoryCheckInCreateReqVO createReqVO) {
        return success(inventoryCheckInService.createInventoryCheckIn(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新签收记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-check-in:update')")
    public CommonResult<Boolean> updateInventoryCheckIn(@Valid @RequestBody InventoryCheckInUpdateReqVO updateReqVO) {
        inventoryCheckInService.updateInventoryCheckIn(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除签收记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-check-in:delete')")
    public CommonResult<Boolean> deleteInventoryCheckIn(@RequestParam("id") Long id) {
        inventoryCheckInService.deleteInventoryCheckIn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得签收记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-check-in:query')")
    public CommonResult<InventoryCheckInRespVO> getInventoryCheckIn(@RequestParam("id") Long id) {
            Optional<InventoryCheckIn> inventoryCheckIn = inventoryCheckInService.getInventoryCheckIn(id);
        return success(inventoryCheckIn.map(inventoryCheckInMapper::toDto).orElseThrow(() -> exception(INVENTORY_CHECK_IN_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得签收记录列表")
    @PreAuthorize("@ss.hasPermission('jl:inventory-check-in:query')")
    public CommonResult<PageResult<InventoryCheckInRespVO>> getInventoryCheckInPage(@Valid InventoryCheckInPageReqVO pageVO, @Valid InventoryCheckInPageOrder orderV0) {
        PageResult<InventoryCheckIn> pageResult = inventoryCheckInService.getInventoryCheckInPage(pageVO, orderV0);
        return success(inventoryCheckInMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出签收记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-check-in:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryCheckInExcel(@Valid InventoryCheckInExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryCheckIn> list = inventoryCheckInService.getInventoryCheckInList(exportReqVO);
        // 导出 Excel
        List<InventoryCheckInExcelVO> excelData = inventoryCheckInMapper.toExcelList(list);
        ExcelUtils.write(response, "签收记录.xls", "数据", InventoryCheckInExcelVO.class, excelData);
    }

}

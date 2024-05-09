package cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog;

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

import cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventorystorelog.InventoryStoreLog;
import cn.iocoder.yudao.module.jl.mapper.inventorystorelog.InventoryStoreLogMapper;
import cn.iocoder.yudao.module.jl.service.inventorystorelog.InventoryStoreLogService;

@Tag(name = "管理后台 - 物品出入库日志")
@RestController
@RequestMapping("/jl/inventory-store-log")
@Validated
public class InventoryStoreLogController {

    @Resource
    private InventoryStoreLogService inventoryStoreLogService;

    @Resource
    private InventoryStoreLogMapper inventoryStoreLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建物品出入库日志")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-log:create')")
    public CommonResult<Long> createInventoryStoreLog(@Valid @RequestBody InventoryStoreLogCreateReqVO createReqVO) {
        return success(inventoryStoreLogService.createInventoryStoreLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物品出入库日志")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-log:update')")
    public CommonResult<Boolean> updateInventoryStoreLog(@Valid @RequestBody InventoryStoreLogUpdateReqVO updateReqVO) {
        inventoryStoreLogService.updateInventoryStoreLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除物品出入库日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-log:delete')")
    public CommonResult<Boolean> deleteInventoryStoreLog(@RequestParam("id") Long id) {
        inventoryStoreLogService.deleteInventoryStoreLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得物品出入库日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-log:query')")
    public CommonResult<InventoryStoreLogRespVO> getInventoryStoreLog(@RequestParam("id") Long id) {
            Optional<InventoryStoreLog> inventoryStoreLog = inventoryStoreLogService.getInventoryStoreLog(id);
        return success(inventoryStoreLog.map(inventoryStoreLogMapper::toDto).orElseThrow(() -> exception(INVENTORY_STORE_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得物品出入库日志列表")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-log:query')")
    public CommonResult<PageResult<InventoryStoreLogRespVO>> getInventoryStoreLogPage(@Valid InventoryStoreLogPageReqVO pageVO, @Valid InventoryStoreLogPageOrder orderV0) {
        PageResult<InventoryStoreLog> pageResult = inventoryStoreLogService.getInventoryStoreLogPage(pageVO, orderV0);
        return success(inventoryStoreLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出物品出入库日志 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-store-log:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryStoreLogExcel(@Valid InventoryStoreLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryStoreLog> list = inventoryStoreLogService.getInventoryStoreLogList(exportReqVO);
        // 导出 Excel
        List<InventoryStoreLogExcelVO> excelData = inventoryStoreLogMapper.toExcelList(list);
        ExcelUtils.write(response, "物品出入库日志.xls", "数据", InventoryStoreLogExcelVO.class, excelData);
    }

}

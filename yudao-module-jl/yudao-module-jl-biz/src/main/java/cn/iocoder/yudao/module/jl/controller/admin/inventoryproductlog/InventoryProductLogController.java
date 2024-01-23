package cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog;

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

import cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventoryproductlog.InventoryProductLog;
import cn.iocoder.yudao.module.jl.mapper.inventoryproductlog.InventoryProductLogMapper;
import cn.iocoder.yudao.module.jl.service.inventoryproductlog.InventoryProductLogService;

@Tag(name = "管理后台 - 产品变更日志")
@RestController
@RequestMapping("/jl/inventory-product-log")
@Validated
public class InventoryProductLogController {

    @Resource
    private InventoryProductLogService inventoryProductLogService;

    @Resource
    private InventoryProductLogMapper inventoryProductLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建产品变更日志")
    @PreAuthorize("@ss.hasPermission('jl:inventory-product-log:create')")
    public CommonResult<Long> createInventoryProductLog(@Valid @RequestBody InventoryProductLogCreateReqVO createReqVO) {
        return success(inventoryProductLogService.createInventoryProductLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品变更日志")
    @PreAuthorize("@ss.hasPermission('jl:inventory-product-log:update')")
    public CommonResult<Boolean> updateInventoryProductLog(@Valid @RequestBody InventoryProductLogUpdateReqVO updateReqVO) {
        inventoryProductLogService.updateInventoryProductLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除产品变更日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-product-log:delete')")
    public CommonResult<Boolean> deleteInventoryProductLog(@RequestParam("id") Long id) {
        inventoryProductLogService.deleteInventoryProductLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得产品变更日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-product-log:query')")
    public CommonResult<InventoryProductLogRespVO> getInventoryProductLog(@RequestParam("id") Long id) {
            Optional<InventoryProductLog> inventoryProductLog = inventoryProductLogService.getInventoryProductLog(id);
        return success(inventoryProductLog.map(inventoryProductLogMapper::toDto).orElseThrow(() -> exception(INVENTORY_PRODUCT_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得产品变更日志列表")
    @PreAuthorize("@ss.hasPermission('jl:inventory-product-log:query')")
    public CommonResult<PageResult<InventoryProductLogRespVO>> getInventoryProductLogPage(@Valid InventoryProductLogPageReqVO pageVO, @Valid InventoryProductLogPageOrder orderV0) {
        PageResult<InventoryProductLog> pageResult = inventoryProductLogService.getInventoryProductLogPage(pageVO, orderV0);
        return success(inventoryProductLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品变更日志 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-product-log:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryProductLogExcel(@Valid InventoryProductLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryProductLog> list = inventoryProductLogService.getInventoryProductLogList(exportReqVO);
        // 导出 Excel
        List<InventoryProductLogExcelVO> excelData = inventoryProductLogMapper.toExcelList(list);
        ExcelUtils.write(response, "产品变更日志.xls", "数据", InventoryProductLogExcelVO.class, excelData);
    }

}

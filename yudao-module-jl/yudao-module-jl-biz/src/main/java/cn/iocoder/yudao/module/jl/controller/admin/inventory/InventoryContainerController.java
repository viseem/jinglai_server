package cn.iocoder.yudao.module.jl.controller.admin.inventory;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainer;
import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryContainerMapper;
import cn.iocoder.yudao.module.jl.service.inventory.InventoryContainerService;

@Tag(name = "管理后台 - 库管存储容器")
@RestController
@RequestMapping("/jl/inventory-container")
@Validated
public class InventoryContainerController {

    @Resource
    private InventoryContainerService inventoryContainerService;

    @Resource
    private InventoryContainerMapper inventoryContainerMapper;

    @PostMapping("/create")
    @Operation(summary = "创建库管存储容器")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container:create')")
    public CommonResult<Long> createInventoryContainer(@Valid @RequestBody InventoryContainerCreateReqVO createReqVO) {
        return success(inventoryContainerService.createInventoryContainer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库管存储容器")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container:update')")
    public CommonResult<Boolean> updateInventoryContainer(@Valid @RequestBody InventoryContainerUpdateReqVO updateReqVO) {
        inventoryContainerService.updateInventoryContainer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除库管存储容器")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-container:delete')")
    public CommonResult<Boolean> deleteInventoryContainer(@RequestParam("id") Long id) {
        inventoryContainerService.deleteInventoryContainer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得库管存储容器")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container:query')")
    public CommonResult<InventoryContainerRespVO> getInventoryContainer(@RequestParam("id") Long id) {
            Optional<InventoryContainer> inventoryContainer = inventoryContainerService.getInventoryContainer(id);
        return success(inventoryContainer.map(inventoryContainerMapper::toDto).orElseThrow(() -> exception(INVENTORY_CONTAINER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得库管存储容器列表")
    public CommonResult<PageResult<InventoryContainerRespVO>> getInventoryContainerPage(@Valid InventoryContainerPageReqVO pageVO, @Valid InventoryContainerPageOrder orderV0) {
        PageResult<InventoryContainer> pageResult = inventoryContainerService.getInventoryContainerPage(pageVO, orderV0);
        return success(inventoryContainerMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库管存储容器 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-container:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryContainerExcel(@Valid InventoryContainerExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryContainer> list = inventoryContainerService.getInventoryContainerList(exportReqVO);
        // 导出 Excel
        List<InventoryContainerExcelVO> excelData = inventoryContainerMapper.toExcelList(list);
        ExcelUtils.write(response, "库管存储容器.xls", "数据", InventoryContainerExcelVO.class, excelData);
    }

}

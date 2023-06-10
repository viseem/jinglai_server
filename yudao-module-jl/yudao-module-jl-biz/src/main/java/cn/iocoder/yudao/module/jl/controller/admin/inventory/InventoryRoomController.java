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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryRoom;
import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryRoomMapper;
import cn.iocoder.yudao.module.jl.service.inventory.InventoryRoomService;

@Tag(name = "管理后台 - 库管房间号")
@RestController
@RequestMapping("/jl/inventory-room")
@Validated
public class InventoryRoomController {

    @Resource
    private InventoryRoomService inventoryRoomService;

    @Resource
    private InventoryRoomMapper inventoryRoomMapper;

    @PostMapping("/create")
    @Operation(summary = "创建库管房间号")
    @PreAuthorize("@ss.hasPermission('jl:inventory-room:create')")
    public CommonResult<Long> createInventoryRoom(@Valid @RequestBody InventoryRoomCreateReqVO createReqVO) {
        return success(inventoryRoomService.createInventoryRoom(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库管房间号")
    @PreAuthorize("@ss.hasPermission('jl:inventory-room:update')")
    public CommonResult<Boolean> updateInventoryRoom(@Valid @RequestBody InventoryRoomUpdateReqVO updateReqVO) {
        inventoryRoomService.updateInventoryRoom(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除库管房间号")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-room:delete')")
    public CommonResult<Boolean> deleteInventoryRoom(@RequestParam("id") Long id) {
        inventoryRoomService.deleteInventoryRoom(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得库管房间号")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-room:query')")
    public CommonResult<InventoryRoomRespVO> getInventoryRoom(@RequestParam("id") Long id) {
            Optional<InventoryRoom> inventoryRoom = inventoryRoomService.getInventoryRoom(id);
        return success(inventoryRoom.map(inventoryRoomMapper::toDto).orElseThrow(() -> exception(INVENTORY_ROOM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得库管房间号列表")
    @PreAuthorize("@ss.hasPermission('jl:inventory-room:query')")
    public CommonResult<PageResult<InventoryRoomRespVO>> getInventoryRoomPage(@Valid InventoryRoomPageReqVO pageVO, @Valid InventoryRoomPageOrder orderV0) {
        PageResult<InventoryRoom> pageResult = inventoryRoomService.getInventoryRoomPage(pageVO, orderV0);
        return success(inventoryRoomMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库管房间号 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-room:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryRoomExcel(@Valid InventoryRoomExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryRoom> list = inventoryRoomService.getInventoryRoomList(exportReqVO);
        // 导出 Excel
        List<InventoryRoomExcelVO> excelData = inventoryRoomMapper.toExcelList(list);
        ExcelUtils.write(response, "库管房间号.xls", "数据", InventoryRoomExcelVO.class, excelData);
    }

}

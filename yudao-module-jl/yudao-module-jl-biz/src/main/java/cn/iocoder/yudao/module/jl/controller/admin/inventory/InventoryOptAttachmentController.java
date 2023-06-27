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
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryOptAttachment;
import cn.iocoder.yudao.module.jl.mapper.inventory.InventoryOptAttachmentMapper;
import cn.iocoder.yudao.module.jl.service.inventory.InventoryOptAttachmentService;

@Tag(name = "管理后台 - 库管操作附件记录")
@RestController
@RequestMapping("/jl/inventory-opt-attachment")
@Validated
public class InventoryOptAttachmentController {

    @Resource
    private InventoryOptAttachmentService inventoryOptAttachmentService;

    @Resource
    private InventoryOptAttachmentMapper inventoryOptAttachmentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建库管操作附件记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-opt-attachment:create')")
    public CommonResult<Long> createInventoryOptAttachment(@Valid @RequestBody InventoryOptAttachmentCreateReqVO createReqVO) {
        return success(inventoryOptAttachmentService.createInventoryOptAttachment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库管操作附件记录")
    @PreAuthorize("@ss.hasPermission('jl:inventory-opt-attachment:update')")
    public CommonResult<Boolean> updateInventoryOptAttachment(@Valid @RequestBody InventoryOptAttachmentUpdateReqVO updateReqVO) {
        inventoryOptAttachmentService.updateInventoryOptAttachment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除库管操作附件记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:inventory-opt-attachment:delete')")
    public CommonResult<Boolean> deleteInventoryOptAttachment(@RequestParam("id") Long id) {
        inventoryOptAttachmentService.deleteInventoryOptAttachment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得库管操作附件记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:inventory-opt-attachment:query')")
    public CommonResult<InventoryOptAttachmentRespVO> getInventoryOptAttachment(@RequestParam("id") Long id) {
            Optional<InventoryOptAttachment> inventoryOptAttachment = inventoryOptAttachmentService.getInventoryOptAttachment(id);
        return success(inventoryOptAttachment.map(inventoryOptAttachmentMapper::toDto).orElseThrow(() -> exception(INVENTORY_OPT_ATTACHMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得库管操作附件记录列表")
    @PreAuthorize("@ss.hasPermission('jl:inventory-opt-attachment:query')")
    public CommonResult<PageResult<InventoryOptAttachmentRespVO>> getInventoryOptAttachmentPage(@Valid InventoryOptAttachmentPageReqVO pageVO, @Valid InventoryOptAttachmentPageOrder orderV0) {
        PageResult<InventoryOptAttachment> pageResult = inventoryOptAttachmentService.getInventoryOptAttachmentPage(pageVO, orderV0);
        return success(inventoryOptAttachmentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库管操作附件记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:inventory-opt-attachment:export')")
    @OperateLog(type = EXPORT)
    public void exportInventoryOptAttachmentExcel(@Valid InventoryOptAttachmentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<InventoryOptAttachment> list = inventoryOptAttachmentService.getInventoryOptAttachmentList(exportReqVO);
        // 导出 Excel
        List<InventoryOptAttachmentExcelVO> excelData = inventoryOptAttachmentMapper.toExcelList(list);
        ExcelUtils.write(response, "库管操作附件记录.xls", "数据", InventoryOptAttachmentExcelVO.class, excelData);
    }

}

package cn.iocoder.yudao.module.jl.controller.admin.project;

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

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyPickupItem;
import cn.iocoder.yudao.module.jl.mapper.project.SupplyPickupItemMapper;
import cn.iocoder.yudao.module.jl.service.project.SupplyPickupItemService;

@Tag(name = "管理后台 - 取货单申请明细")
@RestController
@RequestMapping("/jl/supply-pickup-item")
@Validated
public class SupplyPickupItemController {

    @Resource
    private SupplyPickupItemService supplyPickupItemService;

    @Resource
    private SupplyPickupItemMapper supplyPickupItemMapper;

    @PostMapping("/create")
    @Operation(summary = "创建取货单申请明细")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup-item:create')")
    public CommonResult<Long> createSupplyPickupItem(@Valid @RequestBody SupplyPickupItemCreateReqVO createReqVO) {
        return success(supplyPickupItemService.createSupplyPickupItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新取货单申请明细")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup-item:update')")
    public CommonResult<Boolean> updateSupplyPickupItem(@Valid @RequestBody SupplyPickupItemUpdateReqVO updateReqVO) {
        supplyPickupItemService.updateSupplyPickupItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除取货单申请明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup-item:delete')")
    public CommonResult<Boolean> deleteSupplyPickupItem(@RequestParam("id") Long id) {
        supplyPickupItemService.deleteSupplyPickupItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得取货单申请明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup-item:query')")
    public CommonResult<SupplyPickupItemRespVO> getSupplyPickupItem(@RequestParam("id") Long id) {
            Optional<SupplyPickupItem> supplyPickupItem = supplyPickupItemService.getSupplyPickupItem(id);
        return success(supplyPickupItem.map(supplyPickupItemMapper::toDto).orElseThrow(() -> exception(SUPPLY_PICKUP_ITEM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得取货单申请明细列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup-item:query')")
    public CommonResult<PageResult<SupplyPickupItemRespVO>> getSupplyPickupItemPage(@Valid SupplyPickupItemPageReqVO pageVO, @Valid SupplyPickupItemPageOrder orderV0) {
        PageResult<SupplyPickupItem> pageResult = supplyPickupItemService.getSupplyPickupItemPage(pageVO, orderV0);
        return success(supplyPickupItemMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出取货单申请明细 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-pickup-item:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplyPickupItemExcel(@Valid SupplyPickupItemExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplyPickupItem> list = supplyPickupItemService.getSupplyPickupItemList(exportReqVO);
        // 导出 Excel
        List<SupplyPickupItemExcelVO> excelData = supplyPickupItemMapper.toExcelList(list);
        ExcelUtils.write(response, "取货单申请明细.xls", "数据", SupplyPickupItemExcelVO.class, excelData);
    }

}

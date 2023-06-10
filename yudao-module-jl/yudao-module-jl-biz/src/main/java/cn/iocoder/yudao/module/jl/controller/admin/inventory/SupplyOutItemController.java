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
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import cn.iocoder.yudao.module.jl.mapper.inventory.SupplyOutItemMapper;
import cn.iocoder.yudao.module.jl.service.inventory.SupplyOutItemService;

@Tag(name = "管理后台 - 出库申请明细")
@RestController
@RequestMapping("/jl/supply-out-item")
@Validated
public class SupplyOutItemController {

    @Resource
    private SupplyOutItemService supplyOutItemService;

    @Resource
    private SupplyOutItemMapper supplyOutItemMapper;

    @PostMapping("/create")
    @Operation(summary = "创建出库申请明细")
    @PreAuthorize("@ss.hasPermission('jl:supply-out-item:create')")
    public CommonResult<Long> createSupplyOutItem(@Valid @RequestBody SupplyOutItemCreateReqVO createReqVO) {
        return success(supplyOutItemService.createSupplyOutItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新出库申请明细")
    @PreAuthorize("@ss.hasPermission('jl:supply-out-item:update')")
    public CommonResult<Boolean> updateSupplyOutItem(@Valid @RequestBody SupplyOutItemUpdateReqVO updateReqVO) {
        supplyOutItemService.updateSupplyOutItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除出库申请明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-out-item:delete')")
    public CommonResult<Boolean> deleteSupplyOutItem(@RequestParam("id") Long id) {
        supplyOutItemService.deleteSupplyOutItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得出库申请明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-out-item:query')")
    public CommonResult<SupplyOutItemRespVO> getSupplyOutItem(@RequestParam("id") Long id) {
            Optional<SupplyOutItem> supplyOutItem = supplyOutItemService.getSupplyOutItem(id);
        return success(supplyOutItem.map(supplyOutItemMapper::toDto).orElseThrow(() -> exception(SUPPLY_OUT_ITEM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得出库申请明细列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-out-item:query')")
    public CommonResult<PageResult<SupplyOutItemRespVO>> getSupplyOutItemPage(@Valid SupplyOutItemPageReqVO pageVO, @Valid SupplyOutItemPageOrder orderV0) {
        PageResult<SupplyOutItem> pageResult = supplyOutItemService.getSupplyOutItemPage(pageVO, orderV0);
        return success(supplyOutItemMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出出库申请明细 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-out-item:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplyOutItemExcel(@Valid SupplyOutItemExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplyOutItem> list = supplyOutItemService.getSupplyOutItemList(exportReqVO);
        // 导出 Excel
        List<SupplyOutItemExcelVO> excelData = supplyOutItemMapper.toExcelList(list);
        ExcelUtils.write(response, "出库申请明细.xls", "数据", SupplyOutItemExcelVO.class, excelData);
    }

}

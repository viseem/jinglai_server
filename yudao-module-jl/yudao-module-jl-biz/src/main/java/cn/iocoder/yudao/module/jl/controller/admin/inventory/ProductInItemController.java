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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItem;
import cn.iocoder.yudao.module.jl.mapper.inventory.ProductInItemMapper;
import cn.iocoder.yudao.module.jl.service.inventory.ProductInItemService;

@Tag(name = "管理后台 - 实验产品入库明细")
@RestController
@RequestMapping("/jl/product-in-item")
@Validated
public class ProductInItemController {

    @Resource
    private ProductInItemService productInItemService;

    @Resource
    private ProductInItemMapper productInItemMapper;

    @PostMapping("/create")
    @Operation(summary = "创建实验产品入库明细")
    @PreAuthorize("@ss.hasPermission('jl:product-in-item:create')")
    public CommonResult<Long> createProductInItem(@Valid @RequestBody ProductInItemCreateReqVO createReqVO) {
        return success(productInItemService.createProductInItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新实验产品入库明细")
    @PreAuthorize("@ss.hasPermission('jl:product-in-item:update')")
    public CommonResult<Boolean> updateProductInItem(@Valid @RequestBody ProductInItemUpdateReqVO updateReqVO) {
        productInItemService.updateProductInItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除实验产品入库明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-in-item:delete')")
    public CommonResult<Boolean> deleteProductInItem(@RequestParam("id") Long id) {
        productInItemService.deleteProductInItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验产品入库明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-in-item:query')")
    public CommonResult<ProductInItemRespVO> getProductInItem(@RequestParam("id") Long id) {
            Optional<ProductInItem> productInItem = productInItemService.getProductInItem(id);
        return success(productInItem.map(productInItemMapper::toDto).orElseThrow(() -> exception(PRODUCT_IN_ITEM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验产品入库明细列表")
    @PreAuthorize("@ss.hasPermission('jl:product-in-item:query')")
    public CommonResult<PageResult<ProductInItemRespVO>> getProductInItemPage(@Valid ProductInItemPageReqVO pageVO, @Valid ProductInItemPageOrder orderV0) {
        PageResult<ProductInItem> pageResult = productInItemService.getProductInItemPage(pageVO, orderV0);
        return success(productInItemMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实验产品入库明细 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-in-item:export')")
    @OperateLog(type = EXPORT)
    public void exportProductInItemExcel(@Valid ProductInItemExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductInItem> list = productInItemService.getProductInItemList(exportReqVO);
        // 导出 Excel
        List<ProductInItemExcelVO> excelData = productInItemMapper.toExcelList(list);
        ExcelUtils.write(response, "实验产品入库明细.xls", "数据", ProductInItemExcelVO.class, excelData);
    }

}

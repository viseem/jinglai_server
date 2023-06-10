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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import cn.iocoder.yudao.module.jl.mapper.inventory.ProductSendItemMapper;
import cn.iocoder.yudao.module.jl.service.inventory.ProductSendItemService;

@Tag(name = "管理后台 - 实验产品寄送明细")
@RestController
@RequestMapping("/jl/product-send-item")
@Validated
public class ProductSendItemController {

    @Resource
    private ProductSendItemService productSendItemService;

    @Resource
    private ProductSendItemMapper productSendItemMapper;

    @PostMapping("/create")
    @Operation(summary = "创建实验产品寄送明细")
    @PreAuthorize("@ss.hasPermission('jl:product-send-item:create')")
    public CommonResult<Long> createProductSendItem(@Valid @RequestBody ProductSendItemCreateReqVO createReqVO) {
        return success(productSendItemService.createProductSendItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新实验产品寄送明细")
    @PreAuthorize("@ss.hasPermission('jl:product-send-item:update')")
    public CommonResult<Boolean> updateProductSendItem(@Valid @RequestBody ProductSendItemUpdateReqVO updateReqVO) {
        productSendItemService.updateProductSendItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除实验产品寄送明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-send-item:delete')")
    public CommonResult<Boolean> deleteProductSendItem(@RequestParam("id") Long id) {
        productSendItemService.deleteProductSendItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验产品寄送明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-send-item:query')")
    public CommonResult<ProductSendItemRespVO> getProductSendItem(@RequestParam("id") Long id) {
            Optional<ProductSendItem> productSendItem = productSendItemService.getProductSendItem(id);
        return success(productSendItem.map(productSendItemMapper::toDto).orElseThrow(() -> exception(PRODUCT_SEND_ITEM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验产品寄送明细列表")
    @PreAuthorize("@ss.hasPermission('jl:product-send-item:query')")
    public CommonResult<PageResult<ProductSendItemRespVO>> getProductSendItemPage(@Valid ProductSendItemPageReqVO pageVO, @Valid ProductSendItemPageOrder orderV0) {
        PageResult<ProductSendItem> pageResult = productSendItemService.getProductSendItemPage(pageVO, orderV0);
        return success(productSendItemMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实验产品寄送明细 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-send-item:export')")
    @OperateLog(type = EXPORT)
    public void exportProductSendItemExcel(@Valid ProductSendItemExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductSendItem> list = productSendItemService.getProductSendItemList(exportReqVO);
        // 导出 Excel
        List<ProductSendItemExcelVO> excelData = productSendItemMapper.toExcelList(list);
        ExcelUtils.write(response, "实验产品寄送明细.xls", "数据", ProductSendItemExcelVO.class, excelData);
    }

}

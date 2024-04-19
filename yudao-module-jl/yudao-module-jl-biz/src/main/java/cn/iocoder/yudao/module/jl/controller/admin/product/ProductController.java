package cn.iocoder.yudao.module.jl.controller.admin.product;

import cn.iocoder.yudao.module.jl.entity.product.ProductDetail;
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

import cn.iocoder.yudao.module.jl.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.jl.entity.product.Product;
import cn.iocoder.yudao.module.jl.mapper.product.ProductMapper;
import cn.iocoder.yudao.module.jl.service.product.ProductService;

@Tag(name = "管理后台 - 产品库")
@RestController
@RequestMapping("/jl/product")
@Validated
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private ProductMapper productMapper;

    @PostMapping("/create")
    @Operation(summary = "创建产品库")
    @PreAuthorize("@ss.hasPermission('jl:product:create')")
    public CommonResult<Long> createProduct(@Valid @RequestBody ProductCreateReqVO createReqVO) {
        return success(productService.createProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品库")
    @PreAuthorize("@ss.hasPermission('jl:product:update')")
    public CommonResult<Boolean> updateProduct(@Valid @RequestBody ProductUpdateReqVO updateReqVO) {
        productService.updateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除产品库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product:delete')")
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得产品库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product:query')")
    public CommonResult<ProductRespVO> getProduct(@RequestParam("id") Long id) {
            Optional<ProductDetail> product = productService.getProduct(id);
        return success(product.map(productMapper::toDto).orElseThrow(() -> exception(PRODUCT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得产品库列表")
    @PreAuthorize("@ss.hasPermission('jl:product:query')")
    public CommonResult<PageResult<ProductRespVO>> getProductPage(@Valid ProductPageReqVO pageVO, @Valid ProductPageOrder orderV0) {
        PageResult<Product> pageResult = productService.getProductPage(pageVO, orderV0);
        return success(productMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品库 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product:export')")
    @OperateLog(type = EXPORT)
    public void exportProductExcel(@Valid ProductExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Product> list = productService.getProductList(exportReqVO);
        // 导出 Excel
        List<ProductExcelVO> excelData = productMapper.toExcelList(list);
        ExcelUtils.write(response, "产品库.xls", "数据", ProductExcelVO.class, excelData);
    }

}

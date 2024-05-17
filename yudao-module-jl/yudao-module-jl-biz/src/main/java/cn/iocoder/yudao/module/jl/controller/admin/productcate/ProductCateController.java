package cn.iocoder.yudao.module.jl.controller.admin.productcate;

import cn.iocoder.yudao.module.jl.entity.productcate.ProductCateDetail;
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

import cn.iocoder.yudao.module.jl.controller.admin.productcate.vo.*;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import cn.iocoder.yudao.module.jl.mapper.productcate.ProductCateMapper;
import cn.iocoder.yudao.module.jl.service.productcate.ProductCateService;

@Tag(name = "管理后台 - 产品库分类")
@RestController
@RequestMapping("/jl/product-cate")
@Validated
public class ProductCateController {

    @Resource
    private ProductCateService productCateService;

    @Resource
    private ProductCateMapper productCateMapper;

    @PostMapping("/create")
    @Operation(summary = "创建产品库分类")
    @PreAuthorize("@ss.hasPermission('jl:product-cate:create')")
    public CommonResult<Long> createProductCate(@Valid @RequestBody ProductCateCreateReqVO createReqVO) {
        return success(productCateService.createProductCate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品库分类")
    @PreAuthorize("@ss.hasPermission('jl:product-cate:update')")
    public CommonResult<Boolean> updateProductCate(@Valid @RequestBody ProductCateUpdateReqVO updateReqVO) {
        productCateService.updateProductCate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除产品库分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-cate:delete')")
    public CommonResult<Boolean> deleteProductCate(@RequestParam("id") Long id) {
        productCateService.deleteProductCate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得产品库分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-cate:query')")
    public CommonResult<ProductCateRespVO> getProductCate(@RequestParam("id") Long id) {
            Optional<ProductCateDetail> productCate = productCateService.getProductCate(id);
        return success(productCate.map(productCateMapper::toDto).orElseThrow(() -> exception(PRODUCT_CATE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得产品库分类列表")
    @PreAuthorize("@ss.hasPermission('jl:product-cate:query')")
    public CommonResult<PageResult<ProductCateRespVO>> getProductCatePage(@Valid ProductCatePageReqVO pageVO, @Valid ProductCatePageOrder orderV0) {
        PageResult<ProductCate> pageResult = productCateService.getProductCatePage(pageVO, orderV0);
        return success(productCateMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品库分类 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-cate:export')")
    @OperateLog(type = EXPORT)
    public void exportProductCateExcel(@Valid ProductCateExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductCate> list = productCateService.getProductCateList(exportReqVO);
        // 导出 Excel
        List<ProductCateExcelVO> excelData = productCateMapper.toExcelList(list);
        ExcelUtils.write(response, "产品库分类.xls", "数据", ProductCateExcelVO.class, excelData);
    }

}

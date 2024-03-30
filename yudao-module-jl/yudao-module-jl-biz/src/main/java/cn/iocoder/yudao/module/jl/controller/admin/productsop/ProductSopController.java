package cn.iocoder.yudao.module.jl.controller.admin.productsop;

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

import cn.iocoder.yudao.module.jl.controller.admin.productsop.vo.*;
import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import cn.iocoder.yudao.module.jl.mapper.productsop.ProductSopMapper;
import cn.iocoder.yudao.module.jl.service.productsop.ProductSopService;

@Tag(name = "管理后台 - 产品sop")
@RestController
@RequestMapping("/jl/product-sop")
@Validated
public class ProductSopController {

    @Resource
    private ProductSopService productSopService;

    @Resource
    private ProductSopMapper productSopMapper;

    @PostMapping("/create")
    @Operation(summary = "创建产品sop")
    @PreAuthorize("@ss.hasPermission('jl:product-sop:create')")
    public CommonResult<Long> createProductSop(@Valid @RequestBody ProductSopCreateReqVO createReqVO) {
        return success(productSopService.createProductSop(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品sop")
    @PreAuthorize("@ss.hasPermission('jl:product-sop:update')")
    public CommonResult<Boolean> updateProductSop(@Valid @RequestBody ProductSopUpdateReqVO updateReqVO) {
        productSopService.updateProductSop(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除产品sop")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-sop:delete')")
    public CommonResult<Boolean> deleteProductSop(@RequestParam("id") Long id) {
        productSopService.deleteProductSop(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得产品sop")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-sop:query')")
    public CommonResult<ProductSopRespVO> getProductSop(@RequestParam("id") Long id) {
            Optional<ProductSop> productSop = productSopService.getProductSop(id);
        return success(productSop.map(productSopMapper::toDto).orElseThrow(() -> exception(PRODUCT_SOP_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得产品sop列表")
    @PreAuthorize("@ss.hasPermission('jl:product-sop:query')")
    public CommonResult<PageResult<ProductSopRespVO>> getProductSopPage(@Valid ProductSopPageReqVO pageVO, @Valid ProductSopPageOrder orderV0) {
        PageResult<ProductSop> pageResult = productSopService.getProductSopPage(pageVO, orderV0);
        return success(productSopMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品sop Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-sop:export')")
    @OperateLog(type = EXPORT)
    public void exportProductSopExcel(@Valid ProductSopExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductSop> list = productSopService.getProductSopList(exportReqVO);
        // 导出 Excel
        List<ProductSopExcelVO> excelData = productSopMapper.toExcelList(list);
        ExcelUtils.write(response, "产品sop.xls", "数据", ProductSopExcelVO.class, excelData);
    }

}

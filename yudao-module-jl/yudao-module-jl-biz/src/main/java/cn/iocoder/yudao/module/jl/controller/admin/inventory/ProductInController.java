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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductIn;
import cn.iocoder.yudao.module.jl.mapper.inventory.ProductInMapper;
import cn.iocoder.yudao.module.jl.service.inventory.ProductInService;

@Tag(name = "管理后台 - 实验产品入库")
@RestController
@RequestMapping("/jl/product-in")
@Validated
public class ProductInController {

    @Resource
    private ProductInService productInService;

    @Resource
    private ProductInMapper productInMapper;

    @PostMapping("/create")
    @Operation(summary = "创建实验产品入库")
    @PreAuthorize("@ss.hasPermission('jl:product-in:create')")
    public CommonResult<Long> createProductIn(@Valid @RequestBody ProductInCreateReqVO createReqVO) {
        return success(productInService.createProductIn(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新实验产品入库")
    @PreAuthorize("@ss.hasPermission('jl:product-in:update')")
    public CommonResult<Boolean> updateProductIn(@Valid @RequestBody ProductInUpdateReqVO updateReqVO) {
        productInService.updateProductIn(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除实验产品入库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-in:delete')")
    public CommonResult<Boolean> deleteProductIn(@RequestParam("id") Long id) {
        productInService.deleteProductIn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验产品入库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-in:query')")
    public CommonResult<ProductInRespVO> getProductIn(@RequestParam("id") Long id) {
            Optional<ProductIn> productIn = productInService.getProductIn(id);
        return success(productIn.map(productInMapper::toDto).orElseThrow(() -> exception(PRODUCT_IN_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验产品入库列表")
    @PreAuthorize("@ss.hasPermission('jl:product-in:query')")
    public CommonResult<PageResult<ProductInRespVO>> getProductInPage(@Valid ProductInPageReqVO pageVO, @Valid ProductInPageOrder orderV0) {
        PageResult<ProductIn> pageResult = productInService.getProductInPage(pageVO, orderV0);
        return success(productInMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实验产品入库 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-in:export')")
    @OperateLog(type = EXPORT)
    public void exportProductInExcel(@Valid ProductInExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductIn> list = productInService.getProductInList(exportReqVO);
        // 导出 Excel
        List<ProductInExcelVO> excelData = productInMapper.toExcelList(list);
        ExcelUtils.write(response, "实验产品入库.xls", "数据", ProductInExcelVO.class, excelData);
    }

}

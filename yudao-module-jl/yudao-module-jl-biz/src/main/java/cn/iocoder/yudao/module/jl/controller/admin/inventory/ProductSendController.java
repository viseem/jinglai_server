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
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSend;
import cn.iocoder.yudao.module.jl.mapper.inventory.ProductSendMapper;
import cn.iocoder.yudao.module.jl.service.inventory.ProductSendService;

@Tag(name = "管理后台 - 实验产品寄送")
@RestController
@RequestMapping("/jl/product-send")
@Validated
public class ProductSendController {

    @Resource
    private ProductSendService productSendService;

    @Resource
    private ProductSendMapper productSendMapper;

    @PostMapping("/create")
    @Operation(summary = "创建实验产品寄送")
    @PreAuthorize("@ss.hasPermission('jl:product-send:create')")
    public CommonResult<Long> createProductSend(@Valid @RequestBody ProductSendCreateReqVO createReqVO) {
        return success(productSendService.createProductSend(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新实验产品寄送")
    @PreAuthorize("@ss.hasPermission('jl:product-send:update')")
    public CommonResult<Boolean> updateProductSend(@Valid @RequestBody ProductSendUpdateReqVO updateReqVO) {
        productSendService.updateProductSend(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除实验产品寄送")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-send:delete')")
    public CommonResult<Boolean> deleteProductSend(@RequestParam("id") Long id) {
        productSendService.deleteProductSend(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验产品寄送")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-send:query')")
    public CommonResult<ProductSendRespVO> getProductSend(@RequestParam("id") Long id) {
            Optional<ProductSend> productSend = productSendService.getProductSend(id);
        return success(productSend.map(productSendMapper::toDto).orElseThrow(() -> exception(PRODUCT_SEND_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验产品寄送列表")
    @PreAuthorize("@ss.hasPermission('jl:product-send:query')")
    public CommonResult<PageResult<ProductSendRespVO>> getProductSendPage(@Valid ProductSendPageReqVO pageVO, @Valid ProductSendPageOrder orderV0) {
        PageResult<ProductSend> pageResult = productSendService.getProductSendPage(pageVO, orderV0);
        return success(productSendMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实验产品寄送 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-send:export')")
    @OperateLog(type = EXPORT)
    public void exportProductSendExcel(@Valid ProductSendExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductSend> list = productSendService.getProductSendList(exportReqVO);
        // 导出 Excel
        List<ProductSendExcelVO> excelData = productSendMapper.toExcelList(list);
        ExcelUtils.write(response, "实验产品寄送.xls", "数据", ProductSendExcelVO.class, excelData);
    }

}

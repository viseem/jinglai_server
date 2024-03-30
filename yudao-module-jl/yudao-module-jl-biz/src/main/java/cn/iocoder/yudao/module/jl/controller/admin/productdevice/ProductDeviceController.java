package cn.iocoder.yudao.module.jl.controller.admin.productdevice;

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

import cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo.*;
import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import cn.iocoder.yudao.module.jl.mapper.productdevice.ProductDeviceMapper;
import cn.iocoder.yudao.module.jl.service.productdevice.ProductDeviceService;

@Tag(name = "管理后台 - 产品库设备")
@RestController
@RequestMapping("/jl/product-device")
@Validated
public class ProductDeviceController {

    @Resource
    private ProductDeviceService productDeviceService;

    @Resource
    private ProductDeviceMapper productDeviceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建产品库设备")
    @PreAuthorize("@ss.hasPermission('jl:product-device:create')")
    public CommonResult<Long> createProductDevice(@Valid @RequestBody ProductDeviceCreateReqVO createReqVO) {
        return success(productDeviceService.createProductDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品库设备")
    @PreAuthorize("@ss.hasPermission('jl:product-device:update')")
    public CommonResult<Boolean> updateProductDevice(@Valid @RequestBody ProductDeviceUpdateReqVO updateReqVO) {
        productDeviceService.updateProductDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除产品库设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:product-device:delete')")
    public CommonResult<Boolean> deleteProductDevice(@RequestParam("id") Long id) {
        productDeviceService.deleteProductDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得产品库设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:product-device:query')")
    public CommonResult<ProductDeviceRespVO> getProductDevice(@RequestParam("id") Long id) {
            Optional<ProductDevice> productDevice = productDeviceService.getProductDevice(id);
        return success(productDevice.map(productDeviceMapper::toDto).orElseThrow(() -> exception(PRODUCT_DEVICE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得产品库设备列表")
    @PreAuthorize("@ss.hasPermission('jl:product-device:query')")
    public CommonResult<PageResult<ProductDeviceRespVO>> getProductDevicePage(@Valid ProductDevicePageReqVO pageVO, @Valid ProductDevicePageOrder orderV0) {
        PageResult<ProductDevice> pageResult = productDeviceService.getProductDevicePage(pageVO, orderV0);
        return success(productDeviceMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品库设备 Excel")
    @PreAuthorize("@ss.hasPermission('jl:product-device:export')")
    @OperateLog(type = EXPORT)
    public void exportProductDeviceExcel(@Valid ProductDeviceExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProductDevice> list = productDeviceService.getProductDeviceList(exportReqVO);
        // 导出 Excel
        List<ProductDeviceExcelVO> excelData = productDeviceMapper.toExcelList(list);
        ExcelUtils.write(response, "产品库设备.xls", "数据", ProductDeviceExcelVO.class, excelData);
    }

}

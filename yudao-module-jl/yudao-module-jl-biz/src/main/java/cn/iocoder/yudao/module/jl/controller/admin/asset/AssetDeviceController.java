package cn.iocoder.yudao.module.jl.controller.admin.asset;

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

import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.module.jl.mapper.asset.AssetDeviceMapper;
import cn.iocoder.yudao.module.jl.service.asset.AssetDeviceService;

@Tag(name = "管理后台 - 公司资产（设备）")
@RestController
@RequestMapping("/jl/asset-device")
@Validated
public class AssetDeviceController {

    @Resource
    private AssetDeviceService assetDeviceService;

    @Resource
    private AssetDeviceMapper assetDeviceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建公司资产（设备）")
    @PreAuthorize("@ss.hasPermission('jl:asset-device:create')")
    public CommonResult<Long> createAssetDevice(@Valid @RequestBody AssetDeviceCreateReqVO createReqVO) {
        return success(assetDeviceService.createAssetDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公司资产（设备）")
    @PreAuthorize("@ss.hasPermission('jl:asset-device:update')")
    public CommonResult<Boolean> updateAssetDevice(@Valid @RequestBody AssetDeviceUpdateReqVO updateReqVO) {
        assetDeviceService.updateAssetDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除公司资产（设备）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:asset-device:delete')")
    public CommonResult<Boolean> deleteAssetDevice(@RequestParam("id") Long id) {
        assetDeviceService.deleteAssetDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得公司资产（设备）")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:asset-device:query')")
    public CommonResult<AssetDeviceRespVO> getAssetDevice(@RequestParam("id") Long id) {
            Optional<AssetDevice> assetDevice = assetDeviceService.getAssetDevice(id);
        return success(assetDevice.map(assetDeviceMapper::toDto).orElseThrow(() -> exception(ASSET_DEVICE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得公司资产（设备）列表")
    @PreAuthorize("@ss.hasPermission('jl:asset-device:query')")
    public CommonResult<PageResult<AssetDeviceRespVO>> getAssetDevicePage(@Valid AssetDevicePageReqVO pageVO, @Valid AssetDevicePageOrder orderV0) {
        PageResult<AssetDevice> pageResult = assetDeviceService.getAssetDevicePage(pageVO, orderV0);
        return success(assetDeviceMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公司资产（设备） Excel")
    @PreAuthorize("@ss.hasPermission('jl:asset-device:export')")
    @OperateLog(type = EXPORT)
    public void exportAssetDeviceExcel(@Valid AssetDeviceExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AssetDevice> list = assetDeviceService.getAssetDeviceList(exportReqVO);
        // 导出 Excel
        List<AssetDeviceExcelVO> excelData = assetDeviceMapper.toExcelList(list);
        ExcelUtils.write(response, "公司资产（设备）.xls", "数据", AssetDeviceExcelVO.class, excelData);
    }

}

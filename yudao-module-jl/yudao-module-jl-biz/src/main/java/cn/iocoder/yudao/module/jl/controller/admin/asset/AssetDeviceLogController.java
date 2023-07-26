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
import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import cn.iocoder.yudao.module.jl.mapper.asset.AssetDeviceLogMapper;
import cn.iocoder.yudao.module.jl.service.asset.AssetDeviceLogService;

@Tag(name = "管理后台 - 公司资产（设备）预约")
@RestController
@RequestMapping("/jl/asset-device-log")
@Validated
public class AssetDeviceLogController {

    @Resource
    private AssetDeviceLogService assetDeviceLogService;

    @Resource
    private AssetDeviceLogMapper assetDeviceLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建公司资产（设备）预约")
    @PreAuthorize("@ss.hasPermission('jl:asset-device-log:create')")
    public CommonResult<Long> createAssetDeviceLog(@Valid @RequestBody AssetDeviceLogCreateReqVO createReqVO) {
        return success(assetDeviceLogService.createAssetDeviceLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公司资产（设备）预约")
    @PreAuthorize("@ss.hasPermission('jl:asset-device-log:update')")
    public CommonResult<Boolean> updateAssetDeviceLog(@Valid @RequestBody AssetDeviceLogUpdateReqVO updateReqVO) {
        assetDeviceLogService.updateAssetDeviceLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除公司资产（设备）预约")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:asset-device-log:delete')")
    public CommonResult<Boolean> deleteAssetDeviceLog(@RequestParam("id") Long id) {
        assetDeviceLogService.deleteAssetDeviceLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得公司资产（设备）预约")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:asset-device-log:query')")
    public CommonResult<AssetDeviceLogRespVO> getAssetDeviceLog(@RequestParam("id") Long id) {
            Optional<AssetDeviceLog> assetDeviceLog = assetDeviceLogService.getAssetDeviceLog(id);
        return success(assetDeviceLog.map(assetDeviceLogMapper::toDto).orElseThrow(() -> exception(ASSET_DEVICE_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得公司资产（设备）预约列表")
    @PreAuthorize("@ss.hasPermission('jl:asset-device-log:query')")
    public CommonResult<PageResult<AssetDeviceLogRespVO>> getAssetDeviceLogPage(@Valid AssetDeviceLogPageReqVO pageVO, @Valid AssetDeviceLogPageOrder orderV0) {
        PageResult<AssetDeviceLog> pageResult = assetDeviceLogService.getAssetDeviceLogPage(pageVO, orderV0);
        return success(assetDeviceLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公司资产（设备）预约 Excel")
    @PreAuthorize("@ss.hasPermission('jl:asset-device-log:export')")
    @OperateLog(type = EXPORT)
    public void exportAssetDeviceLogExcel(@Valid AssetDeviceLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AssetDeviceLog> list = assetDeviceLogService.getAssetDeviceLogList(exportReqVO);
        // 导出 Excel
        List<AssetDeviceLogExcelVO> excelData = assetDeviceLogMapper.toExcelList(list);
        ExcelUtils.write(response, "公司资产（设备）预约.xls", "数据", AssetDeviceLogExcelVO.class, excelData);
    }

}

package cn.iocoder.yudao.module.jl.controller.admin.devicecate;

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

import cn.iocoder.yudao.module.jl.controller.admin.devicecate.vo.*;
import cn.iocoder.yudao.module.jl.entity.devicecate.DeviceCate;
import cn.iocoder.yudao.module.jl.mapper.devicecate.DeviceCateMapper;
import cn.iocoder.yudao.module.jl.service.devicecate.DeviceCateService;

@Tag(name = "管理后台 - 设备分类")
@RestController
@RequestMapping("/jl/device-cate")
@Validated
public class DeviceCateController {

    @Resource
    private DeviceCateService deviceCateService;

    @Resource
    private DeviceCateMapper deviceCateMapper;

    @PostMapping("/create")
    @Operation(summary = "创建设备分类")
    @PreAuthorize("@ss.hasPermission('jl:device-cate:create')")
    public CommonResult<Long> createDeviceCate(@Valid @RequestBody DeviceCateCreateReqVO createReqVO) {
        return success(deviceCateService.createDeviceCate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备分类")
    @PreAuthorize("@ss.hasPermission('jl:device-cate:update')")
    public CommonResult<Boolean> updateDeviceCate(@Valid @RequestBody DeviceCateUpdateReqVO updateReqVO) {
        deviceCateService.updateDeviceCate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除设备分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:device-cate:delete')")
    public CommonResult<Boolean> deleteDeviceCate(@RequestParam("id") Long id) {
        deviceCateService.deleteDeviceCate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得设备分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:device-cate:query')")
    public CommonResult<DeviceCateRespVO> getDeviceCate(@RequestParam("id") Long id) {
            Optional<DeviceCate> deviceCate = deviceCateService.getDeviceCate(id);
        return success(deviceCate.map(deviceCateMapper::toDto).orElseThrow(() -> exception(DEVICE_CATE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得设备分类列表")
    @PreAuthorize("@ss.hasPermission('jl:device-cate:query')")
    public CommonResult<PageResult<DeviceCateRespVO>> getDeviceCatePage(@Valid DeviceCatePageReqVO pageVO, @Valid DeviceCatePageOrder orderV0) {
        PageResult<DeviceCate> pageResult = deviceCateService.getDeviceCatePage(pageVO, orderV0);
        return success(deviceCateMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备分类 Excel")
    @PreAuthorize("@ss.hasPermission('jl:device-cate:export')")
    @OperateLog(type = EXPORT)
    public void exportDeviceCateExcel(@Valid DeviceCateExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<DeviceCate> list = deviceCateService.getDeviceCateList(exportReqVO);
        // 导出 Excel
        List<DeviceCateExcelVO> excelData = deviceCateMapper.toExcelList(list);
        ExcelUtils.write(response, "设备分类.xls", "数据", DeviceCateExcelVO.class, excelData);
    }

}

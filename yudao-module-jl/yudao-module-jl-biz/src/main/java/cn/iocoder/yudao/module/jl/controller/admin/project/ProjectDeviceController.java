package cn.iocoder.yudao.module.jl.controller.admin.project;

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

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectDevice;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectDeviceMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectDeviceService;

@Tag(name = "管理后台 - 项目设备")
@RestController
@RequestMapping("/jl/project-device")
@Validated
public class ProjectDeviceController {

    @Resource
    private ProjectDeviceService projectDeviceService;

    @Resource
    private ProjectDeviceMapper projectDeviceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目设备")
    @PreAuthorize("@ss.hasPermission('jl:project-device:create')")
    public CommonResult<Long> createProjectDevice(@Valid @RequestBody ProjectDeviceCreateReqVO createReqVO) {
        return success(projectDeviceService.createProjectDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目设备")
    @PreAuthorize("@ss.hasPermission('jl:project-device:update')")
    public CommonResult<Boolean> updateProjectDevice(@Valid @RequestBody ProjectDeviceUpdateReqVO updateReqVO) {
        projectDeviceService.updateProjectDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-device:delete')")
    public CommonResult<Boolean> deleteProjectDevice(@RequestParam("id") Long id) {
        projectDeviceService.deleteProjectDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-device:query')")
    public CommonResult<ProjectDeviceRespVO> getProjectDevice(@RequestParam("id") Long id) {
            Optional<ProjectDevice> projectDevice = projectDeviceService.getProjectDevice(id);
        return success(projectDevice.map(projectDeviceMapper::toDto).orElseThrow(() -> exception(PROJECT_DEVICE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目设备列表")
    @PreAuthorize("@ss.hasPermission('jl:project-device:query')")
    public CommonResult<PageResult<ProjectDeviceRespVO>> getProjectDevicePage(@Valid ProjectDevicePageReqVO pageVO, @Valid ProjectDevicePageOrder orderV0) {
        PageResult<ProjectDevice> pageResult = projectDeviceService.getProjectDevicePage(pageVO, orderV0);
        return success(projectDeviceMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目设备 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-device:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectDeviceExcel(@Valid ProjectDeviceExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectDevice> list = projectDeviceService.getProjectDeviceList(exportReqVO);
        // 导出 Excel
        List<ProjectDeviceExcelVO> excelData = projectDeviceMapper.toExcelList(list);
        ExcelUtils.write(response, "项目设备.xls", "数据", ProjectDeviceExcelVO.class, excelData);
    }

}

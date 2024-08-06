package cn.iocoder.yudao.module.jl.controller.admin.projectoutlog;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectoutlog.ProjectOutLog;
import cn.iocoder.yudao.module.jl.mapper.projectoutlog.ProjectOutLogMapper;
import cn.iocoder.yudao.module.jl.service.projectoutlog.ProjectOutLogService;

@Tag(name = "管理后台 - example 空")
@RestController
@RequestMapping("/jl/project-out-log")
@Validated
public class ProjectOutLogController {

    @Resource
    private ProjectOutLogService projectOutLogService;

    @Resource
    private ProjectOutLogMapper projectOutLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建example 空")
    @PreAuthorize("@ss.hasPermission('jl:project-out-log:create')")
    public CommonResult<Long> createProjectOutLog(@Valid @RequestBody ProjectOutLogCreateReqVO createReqVO) {
        return success(projectOutLogService.createProjectOutLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新example 空")
    @PreAuthorize("@ss.hasPermission('jl:project-out-log:update')")
    public CommonResult<Boolean> updateProjectOutLog(@Valid @RequestBody ProjectOutLogUpdateReqVO updateReqVO) {
        projectOutLogService.updateProjectOutLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除example 空")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-out-log:delete')")
    public CommonResult<Boolean> deleteProjectOutLog(@RequestParam("id") Long id) {
        projectOutLogService.deleteProjectOutLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得example 空")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-out-log:query')")
    public CommonResult<ProjectOutLogRespVO> getProjectOutLog(@RequestParam("id") Long id) {
            Optional<ProjectOutLog> projectOutLog = projectOutLogService.getProjectOutLog(id);
        return success(projectOutLog.map(projectOutLogMapper::toDto).orElseThrow(() -> exception(PROJECT_OUT_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得example 空列表")
    @PreAuthorize("@ss.hasPermission('jl:project-out-log:query')")
    public CommonResult<PageResult<ProjectOutLogRespVO>> getProjectOutLogPage(@Valid ProjectOutLogPageReqVO pageVO, @Valid ProjectOutLogPageOrder orderV0) {
        PageResult<ProjectOutLog> pageResult = projectOutLogService.getProjectOutLogPage(pageVO, orderV0);
        return success(projectOutLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出example 空 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-out-log:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectOutLogExcel(@Valid ProjectOutLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectOutLog> list = projectOutLogService.getProjectOutLogList(exportReqVO);
        // 导出 Excel
        List<ProjectOutLogExcelVO> excelData = projectOutLogMapper.toExcelList(list);
        ExcelUtils.write(response, "example 空.xls", "数据", ProjectOutLogExcelVO.class, excelData);
    }

}

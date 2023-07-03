package cn.iocoder.yudao.module.jl.controller.admin.projectfundlog;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.module.jl.mapper.projectfundlog.ProjectFundLogMapper;
import cn.iocoder.yudao.module.jl.service.projectfundlog.ProjectFundLogService;

@Tag(name = "管理后台 - 项目款项")
@RestController
@RequestMapping("/jl/project-fund-log")
@Validated
public class ProjectFundLogController {

    @Resource
    private ProjectFundLogService projectFundLogService;

    @Resource
    private ProjectFundLogMapper projectFundLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目款项")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-log:create')")
    public CommonResult<Long> createProjectFundLog(@Valid @RequestBody ProjectFundLogCreateReqVO createReqVO) {
        return success(projectFundLogService.createProjectFundLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目款项")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-log:update')")
    public CommonResult<Boolean> updateProjectFundLog(@Valid @RequestBody ProjectFundLogUpdateReqVO updateReqVO) {
        projectFundLogService.updateProjectFundLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目款项")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-fund-log:delete')")
    public CommonResult<Boolean> deleteProjectFundLog(@RequestParam("id") Long id) {
        projectFundLogService.deleteProjectFundLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目款项")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-log:query')")
    public CommonResult<ProjectFundLogRespVO> getProjectFundLog(@RequestParam("id") Long id) {
            Optional<ProjectFundLog> projectFundLog = projectFundLogService.getProjectFundLog(id);
        return success(projectFundLog.map(projectFundLogMapper::toDto).orElseThrow(() -> exception(PROJECT_FUND_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目款项列表")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-log:query')")
    public CommonResult<PageResult<ProjectFundLogRespVO>> getProjectFundLogPage(@Valid ProjectFundLogPageReqVO pageVO, @Valid ProjectFundLogPageOrder orderV0) {
        PageResult<ProjectFundLog> pageResult = projectFundLogService.getProjectFundLogPage(pageVO, orderV0);
        return success(projectFundLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目款项 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-log:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectFundLogExcel(@Valid ProjectFundLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectFundLog> list = projectFundLogService.getProjectFundLogList(exportReqVO);
        // 导出 Excel
        List<ProjectFundLogExcelVO> excelData = projectFundLogMapper.toExcelList(list);
        ExcelUtils.write(response, "项目款项.xls", "数据", ProjectFundLogExcelVO.class, excelData);
    }

}

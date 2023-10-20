package cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundchangelog.ProjectFundChangeLog;
import cn.iocoder.yudao.module.jl.mapper.projectfundchangelog.ProjectFundChangeLogMapper;
import cn.iocoder.yudao.module.jl.service.projectfundchangelog.ProjectFundChangeLogService;

@Tag(name = "管理后台 - 款项计划变更日志")
@RestController
@RequestMapping("/jl/project-fund-change-log")
@Validated
public class ProjectFundChangeLogController {

    @Resource
    private ProjectFundChangeLogService projectFundChangeLogService;

    @Resource
    private ProjectFundChangeLogMapper projectFundChangeLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建款项计划变更日志")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-change-log:create')")
    public CommonResult<Long> createProjectFundChangeLog(@Valid @RequestBody ProjectFundChangeLogCreateReqVO createReqVO) {
        return success(projectFundChangeLogService.createProjectFundChangeLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新款项计划变更日志")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-change-log:update')")
    public CommonResult<Boolean> updateProjectFundChangeLog(@Valid @RequestBody ProjectFundChangeLogUpdateReqVO updateReqVO) {
        projectFundChangeLogService.updateProjectFundChangeLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除款项计划变更日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-fund-change-log:delete')")
    public CommonResult<Boolean> deleteProjectFundChangeLog(@RequestParam("id") Long id) {
        projectFundChangeLogService.deleteProjectFundChangeLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得款项计划变更日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-change-log:query')")
    public CommonResult<ProjectFundChangeLogRespVO> getProjectFundChangeLog(@RequestParam("id") Long id) {
            Optional<ProjectFundChangeLog> projectFundChangeLog = projectFundChangeLogService.getProjectFundChangeLog(id);
        return success(projectFundChangeLog.map(projectFundChangeLogMapper::toDto).orElseThrow(() -> exception(PROJECT_FUND_CHANGE_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得款项计划变更日志列表")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-change-log:query')")
    public CommonResult<PageResult<ProjectFundChangeLogRespVO>> getProjectFundChangeLogPage(@Valid ProjectFundChangeLogPageReqVO pageVO, @Valid ProjectFundChangeLogPageOrder orderV0) {
        PageResult<ProjectFundChangeLog> pageResult = projectFundChangeLogService.getProjectFundChangeLogPage(pageVO, orderV0);
        return success(projectFundChangeLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出款项计划变更日志 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-fund-change-log:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectFundChangeLogExcel(@Valid ProjectFundChangeLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectFundChangeLog> list = projectFundChangeLogService.getProjectFundChangeLogList(exportReqVO);
        // 导出 Excel
        List<ProjectFundChangeLogExcelVO> excelData = projectFundChangeLogMapper.toExcelList(list);
        ExcelUtils.write(response, "款项计划变更日志.xls", "数据", ProjectFundChangeLogExcelVO.class, excelData);
    }

}

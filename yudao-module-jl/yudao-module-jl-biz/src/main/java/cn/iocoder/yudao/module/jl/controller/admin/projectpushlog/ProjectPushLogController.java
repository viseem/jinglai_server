package cn.iocoder.yudao.module.jl.controller.admin.projectpushlog;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectpushlog.ProjectPushLog;
import cn.iocoder.yudao.module.jl.mapper.projectpushlog.ProjectPushLogMapper;
import cn.iocoder.yudao.module.jl.service.projectpushlog.ProjectPushLogService;

@Tag(name = "管理后台 - 项目推进记录")
@RestController
@RequestMapping("/jl/project-push-log")
@Validated
public class ProjectPushLogController {

    @Resource
    private ProjectPushLogService projectPushLogService;

    @Resource
    private ProjectPushLogMapper projectPushLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目推进记录")
    @PreAuthorize("@ss.hasPermission('jl:project-push-log:create')")
    public CommonResult<Long> createProjectPushLog(@Valid @RequestBody ProjectPushLogCreateReqVO createReqVO) {
        return success(projectPushLogService.createProjectPushLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目推进记录")
    @PreAuthorize("@ss.hasPermission('jl:project-push-log:update')")
    public CommonResult<Boolean> updateProjectPushLog(@Valid @RequestBody ProjectPushLogUpdateReqVO updateReqVO) {
        projectPushLogService.updateProjectPushLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目推进记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-push-log:delete')")
    public CommonResult<Boolean> deleteProjectPushLog(@RequestParam("id") Long id) {
        projectPushLogService.deleteProjectPushLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目推进记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-push-log:query')")
    public CommonResult<ProjectPushLogRespVO> getProjectPushLog(@RequestParam("id") Long id) {
            Optional<ProjectPushLog> projectPushLog = projectPushLogService.getProjectPushLog(id);
        return success(projectPushLog.map(projectPushLogMapper::toDto).orElseThrow(() -> exception(PROJECT_PUSH_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目推进记录列表")
    @PreAuthorize("@ss.hasPermission('jl:project-push-log:query')")
    public CommonResult<PageResult<ProjectPushLogRespVO>> getProjectPushLogPage(@Valid ProjectPushLogPageReqVO pageVO, @Valid ProjectPushLogPageOrder orderV0) {
        PageResult<ProjectPushLog> pageResult = projectPushLogService.getProjectPushLogPage(pageVO, orderV0);
        return success(projectPushLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目推进记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-push-log:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectPushLogExcel(@Valid ProjectPushLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectPushLog> list = projectPushLogService.getProjectPushLogList(exportReqVO);
        // 导出 Excel
        List<ProjectPushLogExcelVO> excelData = projectPushLogMapper.toExcelList(list);
        ExcelUtils.write(response, "项目推进记录.xls", "数据", ProjectPushLogExcelVO.class, excelData);
    }

}

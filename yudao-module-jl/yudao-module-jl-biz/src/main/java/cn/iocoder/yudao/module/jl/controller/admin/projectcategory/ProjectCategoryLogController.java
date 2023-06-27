package cn.iocoder.yudao.module.jl.controller.admin.projectcategory;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryLogMapper;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryLogService;

@Tag(name = "管理后台 - 实验名目的操作记录")
@RestController
@RequestMapping("/jl/project-category-log")
@Validated
public class ProjectCategoryLogController {

    @Resource
    private ProjectCategoryLogService projectCategoryLogService;

    @Resource
    private ProjectCategoryLogMapper projectCategoryLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建实验名目的操作记录")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:create')")
    public CommonResult<Long> createProjectCategoryLog(@Valid @RequestBody ProjectCategoryLogCreateReqVO createReqVO) {
        return success(projectCategoryLogService.createProjectCategoryLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新实验名目的操作记录")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:update')")
    public CommonResult<Boolean> updateProjectCategoryLog(@Valid @RequestBody ProjectCategoryLogUpdateReqVO updateReqVO) {
        projectCategoryLogService.updateProjectCategoryLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除实验名目的操作记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:delete')")
    public CommonResult<Boolean> deleteProjectCategoryLog(@RequestParam("id") Long id) {
        projectCategoryLogService.deleteProjectCategoryLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验名目的操作记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:query')")
    public CommonResult<ProjectCategoryLogRespVO> getProjectCategoryLog(@RequestParam("id") Long id) {
            Optional<ProjectCategoryLog> projectCategoryLog = projectCategoryLogService.getProjectCategoryLog(id);
        return success(projectCategoryLog.map(projectCategoryLogMapper::toDto).orElseThrow(() -> exception(PROJECT_CATEGORY_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验名目的操作记录列表")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:query')")
    public CommonResult<PageResult<ProjectCategoryLogRespVO>> getProjectCategoryLogPage(@Valid ProjectCategoryLogPageReqVO pageVO, @Valid ProjectCategoryLogPageOrder orderV0) {
        PageResult<ProjectCategoryLog> pageResult = projectCategoryLogService.getProjectCategoryLogPage(pageVO, orderV0);
        return success(projectCategoryLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实验名目的操作记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectCategoryLogExcel(@Valid ProjectCategoryLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectCategoryLog> list = projectCategoryLogService.getProjectCategoryLogList(exportReqVO);
        // 导出 Excel
        List<ProjectCategoryLogExcelVO> excelData = projectCategoryLogMapper.toExcelList(list);
        ExcelUtils.write(response, "实验名目的操作记录.xls", "数据", ProjectCategoryLogExcelVO.class, excelData);
    }

}

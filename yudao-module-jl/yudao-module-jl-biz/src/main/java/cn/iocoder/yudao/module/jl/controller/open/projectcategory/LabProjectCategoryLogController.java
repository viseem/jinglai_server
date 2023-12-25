package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryLogMapper;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_CATEGORY_LOG_NOT_EXISTS;

@Tag(name = "管理后台 - 项目实验名目的操作日志")
@RestController
@RequestMapping("/lab/exp-log")
@Validated
public class LabProjectCategoryLogController {

    @Resource
    private ProjectCategoryLogService projectCategoryLogService;

    @Resource
    private ProjectCategoryLogMapper projectCategoryLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目实验名目的操作日志")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:create')")
    public CommonResult<Long> createProjectCategoryLog(@Valid @RequestBody ProjectCategoryLogCreateReqVO createReqVO) {
        return success(projectCategoryLogService.createProjectCategoryLog(createReqVO));
    }

    @PutMapping("/save")
    @Operation(summary = "更新项目实验名目的操作日志")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:update')")
    public CommonResult<Boolean> saveProjectCategoryLog(@Valid @RequestBody ProjectCategoryLogSaveReqVO saveReqVO) {
        projectCategoryLogService.saveProjectCategoryLog(saveReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目实验名目的操作日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:delete')")
    public CommonResult<Boolean> deleteProjectCategoryLog(@RequestParam("id") Long id) {
        projectCategoryLogService.deleteProjectCategoryLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目实验名目的操作日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-category-log:query')")
    public CommonResult<ProjectCategoryLogRespVO> getProjectCategoryLog(@RequestParam("id") Long id) {
            Optional<ProjectCategoryLog> projectCategoryLog = projectCategoryLogService.getProjectCategoryLog(id);
        return success(projectCategoryLog.map(projectCategoryLogMapper::toDto).orElseThrow(() -> exception(PROJECT_CATEGORY_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @PermitAll
    @Operation(summary = "(分页)获得项目实验名目的操作日志列表")
    public CommonResult<PageResult<ProjectCategoryLogRespVO>> getProjectCategoryLogPage(@Valid ProjectCategoryLogPageReqVO pageVO, @Valid ProjectCategoryLogPageOrder orderV0) {
        PageResult<ProjectCategoryLog> pageResult = projectCategoryLogService.getProjectCategoryLogPage(pageVO, orderV0);
        return success(projectCategoryLogMapper.toPage(pageResult));
    }


}

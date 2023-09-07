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
import cn.iocoder.yudao.module.jl.entity.project.ProjectPlan;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectPlanMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectPlanService;

@Tag(name = "管理后台 - 项目实验方案")
@RestController
@RequestMapping("/jl/project-plan")
@Validated
public class ProjectPlanController {

    @Resource
    private ProjectPlanService projectPlanService;

    @Resource
    private ProjectPlanMapper projectPlanMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目实验方案")
    @PreAuthorize("@ss.hasPermission('jl:project-plan:create')")
    public CommonResult<Long> createProjectPlan(@Valid @RequestBody ProjectPlanCreateReqVO createReqVO) {
        return success(projectPlanService.createProjectPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目实验方案")
    @PreAuthorize("@ss.hasPermission('jl:project-plan:update')")
    public CommonResult<Boolean> updateProjectPlan(@Valid @RequestBody ProjectPlanUpdateReqVO updateReqVO) {
        projectPlanService.updateProjectPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目实验方案")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-plan:delete')")
    public CommonResult<Boolean> deleteProjectPlan(@RequestParam("id") Long id) {
        projectPlanService.deleteProjectPlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目实验方案")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-plan:query')")
    public CommonResult<ProjectPlanRespVO> getProjectPlan(@RequestParam("id") Long id) {
            Optional<ProjectPlan> projectPlan = projectPlanService.getProjectPlan(id);
        return success(projectPlan.map(projectPlanMapper::toDto).orElseThrow(() -> exception(PROJECT_PLAN_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目实验方案列表")
    @PreAuthorize("@ss.hasPermission('jl:project-plan:query')")
    public CommonResult<PageResult<ProjectPlanRespVO>> getProjectPlanPage(@Valid ProjectPlanPageReqVO pageVO, @Valid ProjectPlanPageOrder orderV0) {
        PageResult<ProjectPlan> pageResult = projectPlanService.getProjectPlanPage(pageVO, orderV0);
        return success(projectPlanMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目实验方案 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-plan:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectPlanExcel(@Valid ProjectPlanExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectPlan> list = projectPlanService.getProjectPlanList(exportReqVO);
        // 导出 Excel
        List<ProjectPlanExcelVO> excelData = projectPlanMapper.toExcelList(list);
        ExcelUtils.write(response, "项目实验方案.xls", "数据", ProjectPlanExcelVO.class, excelData);
    }

}

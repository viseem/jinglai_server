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
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryOutsourceMapper;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryOutsourceService;

@Tag(name = "管理后台 - 项目实验委外")
@RestController
@RequestMapping("/jl/project-category-outsource")
@Validated
public class ProjectCategoryOutsourceController {

    @Resource
    private ProjectCategoryOutsourceService projectCategoryOutsourceService;

    @Resource
    private ProjectCategoryOutsourceMapper projectCategoryOutsourceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目实验委外")
    @PreAuthorize("@ss.hasPermission('jl:project-category-outsource:create')")
    public CommonResult<Long> createProjectCategoryOutsource(@Valid @RequestBody ProjectCategoryOutsourceCreateReqVO createReqVO) {
        return success(projectCategoryOutsourceService.createProjectCategoryOutsource(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目实验委外")
    @PreAuthorize("@ss.hasPermission('jl:project-category-outsource:update')")
    public CommonResult<Boolean> updateProjectCategoryOutsource(@Valid @RequestBody ProjectCategoryOutsourceUpdateReqVO updateReqVO) {
        projectCategoryOutsourceService.updateProjectCategoryOutsource(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目实验委外")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-category-outsource:delete')")
    public CommonResult<Boolean> deleteProjectCategoryOutsource(@RequestParam("id") Long id) {
        projectCategoryOutsourceService.deleteProjectCategoryOutsource(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目实验委外")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-category-outsource:query')")
    public CommonResult<ProjectCategoryOutsourceRespVO> getProjectCategoryOutsource(@RequestParam("id") Long id) {
            Optional<ProjectCategoryOutsource> projectCategoryOutsource = projectCategoryOutsourceService.getProjectCategoryOutsource(id);
        return success(projectCategoryOutsource.map(projectCategoryOutsourceMapper::toDto).orElseThrow(() -> exception(PROJECT_CATEGORY_OUTSOURCE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目实验委外列表")
    @PreAuthorize("@ss.hasPermission('jl:project-category-outsource:query')")
    public CommonResult<PageResult<ProjectCategoryOutsourceRespVO>> getProjectCategoryOutsourcePage(@Valid ProjectCategoryOutsourcePageReqVO pageVO, @Valid ProjectCategoryOutsourcePageOrder orderV0) {
        PageResult<ProjectCategoryOutsource> pageResult = projectCategoryOutsourceService.getProjectCategoryOutsourcePage(pageVO, orderV0);
        return success(projectCategoryOutsourceMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目实验委外 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-category-outsource:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectCategoryOutsourceExcel(@Valid ProjectCategoryOutsourceExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectCategoryOutsource> list = projectCategoryOutsourceService.getProjectCategoryOutsourceList(exportReqVO);
        // 导出 Excel
        List<ProjectCategoryOutsourceExcelVO> excelData = projectCategoryOutsourceMapper.toExcelList(list);
        ExcelUtils.write(response, "项目实验委外.xls", "数据", ProjectCategoryOutsourceExcelVO.class, excelData);
    }

}

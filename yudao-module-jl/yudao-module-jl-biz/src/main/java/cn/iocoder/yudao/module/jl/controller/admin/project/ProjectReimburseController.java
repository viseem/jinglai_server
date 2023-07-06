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
import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectReimburseMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectReimburseService;

@Tag(name = "管理后台 - 项目报销")
@RestController
@RequestMapping("/jl/project-reimburse")
@Validated
public class ProjectReimburseController {

    @Resource
    private ProjectReimburseService projectReimburseService;

    @Resource
    private ProjectReimburseMapper projectReimburseMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目报销")
    @PreAuthorize("@ss.hasPermission('jl:project-reimburse:create')")
    public CommonResult<Long> createProjectReimburse(@Valid @RequestBody ProjectReimburseCreateReqVO createReqVO) {
        return success(projectReimburseService.createProjectReimburse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目报销")
    @PreAuthorize("@ss.hasPermission('jl:project-reimburse:update')")
    public CommonResult<Boolean> updateProjectReimburse(@Valid @RequestBody ProjectReimburseUpdateReqVO updateReqVO) {
        projectReimburseService.updateProjectReimburse(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目报销")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-reimburse:delete')")
    public CommonResult<Boolean> deleteProjectReimburse(@RequestParam("id") Long id) {
        projectReimburseService.deleteProjectReimburse(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目报销")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-reimburse:query')")
    public CommonResult<ProjectReimburseRespVO> getProjectReimburse(@RequestParam("id") Long id) {
            Optional<ProjectReimburse> projectReimburse = projectReimburseService.getProjectReimburse(id);
        return success(projectReimburse.map(projectReimburseMapper::toDto).orElseThrow(() -> exception(PROJECT_REIMBURSE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目报销列表")
    @PreAuthorize("@ss.hasPermission('jl:project-reimburse:query')")
    public CommonResult<PageResult<ProjectReimburseRespVO>> getProjectReimbursePage(@Valid ProjectReimbursePageReqVO pageVO, @Valid ProjectReimbursePageOrder orderV0) {
        PageResult<ProjectReimburse> pageResult = projectReimburseService.getProjectReimbursePage(pageVO, orderV0);
        return success(projectReimburseMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目报销 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-reimburse:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectReimburseExcel(@Valid ProjectReimburseExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectReimburse> list = projectReimburseService.getProjectReimburseList(exportReqVO);
        // 导出 Excel
        List<ProjectReimburseExcelVO> excelData = projectReimburseMapper.toExcelList(list);
        ExcelUtils.write(response, "项目报销.xls", "数据", ProjectReimburseExcelVO.class, excelData);
    }

}

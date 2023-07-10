package cn.iocoder.yudao.module.jl.controller.admin.projectservice;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectservice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectservice.ProjectService;
import cn.iocoder.yudao.module.jl.mapper.projectservice.ProjectServiceMapper;
import cn.iocoder.yudao.module.jl.service.projectservice.ProjectServiceService;

@Tag(name = "管理后台 - 项目售后")
@RestController
@RequestMapping("/jl/project-service")
@Validated
public class ProjectServiceController {

    @Resource
    private ProjectServiceService projectServiceService;

    @Resource
    private ProjectServiceMapper projectServiceMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目售后")
    @PreAuthorize("@ss.hasPermission('jl:project-service:create')")
    public CommonResult<Long> createProjectService(@Valid @RequestBody ProjectServiceCreateReqVO createReqVO) {
        return success(projectServiceService.createProjectService(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目售后")
    @PreAuthorize("@ss.hasPermission('jl:project-service:update')")
    public CommonResult<Boolean> updateProjectService(@Valid @RequestBody ProjectServiceUpdateReqVO updateReqVO) {
        projectServiceService.updateProjectService(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目售后")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-service:delete')")
    public CommonResult<Boolean> deleteProjectService(@RequestParam("id") Long id) {
        projectServiceService.deleteProjectService(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目售后")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-service:query')")
    public CommonResult<ProjectServiceRespVO> getProjectService(@RequestParam("id") Long id) {
            Optional<ProjectService> projectService = projectServiceService.getProjectService(id);
        return success(projectService.map(projectServiceMapper::toDto).orElseThrow(() -> exception(PROJECT_SERVICE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目售后列表")
    @PreAuthorize("@ss.hasPermission('jl:project-service:query')")
    public CommonResult<PageResult<ProjectServiceRespVO>> getProjectServicePage(@Valid ProjectServicePageReqVO pageVO, @Valid ProjectServicePageOrder orderV0) {
        PageResult<ProjectService> pageResult = projectServiceService.getProjectServicePage(pageVO, orderV0);
        return success(projectServiceMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目售后 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-service:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectServiceExcel(@Valid ProjectServiceExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectService> list = projectServiceService.getProjectServiceList(exportReqVO);
        // 导出 Excel
        List<ProjectServiceExcelVO> excelData = projectServiceMapper.toExcelList(list);
        ExcelUtils.write(response, "项目售后.xls", "数据", ProjectServiceExcelVO.class, excelData);
    }

}

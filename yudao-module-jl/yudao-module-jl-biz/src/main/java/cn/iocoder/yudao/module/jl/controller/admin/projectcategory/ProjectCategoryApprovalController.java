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
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryApprovalMapper;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryApprovalService;

@Tag(name = "管理后台 - 项目实验名目的状态变更审批")
@RestController
@RequestMapping("/jl/project-category-approval")
@Validated
public class ProjectCategoryApprovalController {

    @Resource
    private ProjectCategoryApprovalService projectCategoryApprovalService;

    @Resource
    private ProjectCategoryApprovalMapper projectCategoryApprovalMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目实验名目的状态变更审批")
    @PreAuthorize("@ss.hasPermission('jl:project-category-approval:create')")
    public CommonResult<Long> createProjectCategoryApproval(@Valid @RequestBody ProjectCategoryApprovalCreateReqVO createReqVO) {
        return success(projectCategoryApprovalService.createProjectCategoryApproval(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目实验名目的状态变更审批")
    @PreAuthorize("@ss.hasPermission('jl:project-category-approval:update')")
    public CommonResult<Boolean> updateProjectCategoryApproval(@Valid @RequestBody ProjectCategoryApprovalUpdateReqVO updateReqVO) {
        projectCategoryApprovalService.updateProjectCategoryApproval(updateReqVO);
        return success(true);
    }

    @PutMapping("/save")
    @Operation(summary = "save更新项目实验名目的状态变更审批")
    @PreAuthorize("@ss.hasPermission('jl:project-category-approval:update')")
    public CommonResult<Boolean> saveProjectCategoryApproval(@Valid @RequestBody ProjectCategoryApprovalSaveReqVO saveReqVO) {
        projectCategoryApprovalService.saveProjectCategoryApproval(saveReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目实验名目的状态变更审批")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-category-approval:delete')")
    public CommonResult<Boolean> deleteProjectCategoryApproval(@RequestParam("id") Long id) {
        projectCategoryApprovalService.deleteProjectCategoryApproval(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目实验名目的状态变更审批")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-category-approval:query')")
    public CommonResult<ProjectCategoryApprovalRespVO> getProjectCategoryApproval(@RequestParam("id") Long id) {
            Optional<ProjectCategoryApproval> projectCategoryApproval = projectCategoryApprovalService.getProjectCategoryApproval(id);
        return success(projectCategoryApproval.map(projectCategoryApprovalMapper::toDto).orElseThrow(() -> exception(PROJECT_CATEGORY_APPROVAL_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目实验名目的状态变更审批列表")
    @PreAuthorize("@ss.hasPermission('jl:project-category-approval:query')")
    public CommonResult<PageResult<ProjectCategoryApprovalRespVO>> getProjectCategoryApprovalPage(@Valid ProjectCategoryApprovalPageReqVO pageVO, @Valid ProjectCategoryApprovalPageOrder orderV0) {
        PageResult<ProjectCategoryApproval> pageResult = projectCategoryApprovalService.getProjectCategoryApprovalPage(pageVO, orderV0);
        return success(projectCategoryApprovalMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目实验名目的状态变更审批 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-category-approval:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectCategoryApprovalExcel(@Valid ProjectCategoryApprovalExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectCategoryApproval> list = projectCategoryApprovalService.getProjectCategoryApprovalList(exportReqVO);
        // 导出 Excel
        List<ProjectCategoryApprovalExcelVO> excelData = projectCategoryApprovalMapper.toExcelList(list);
        ExcelUtils.write(response, "项目实验名目的状态变更审批.xls", "数据", ProjectCategoryApprovalExcelVO.class, excelData);
    }

}

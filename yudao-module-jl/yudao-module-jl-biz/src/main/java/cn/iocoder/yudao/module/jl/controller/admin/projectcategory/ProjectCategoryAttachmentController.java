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
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryAttachmentMapper;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryAttachmentService;

@Tag(name = "管理后台 - 项目实验名目附件")
@RestController
@RequestMapping("/jl/project-category-attachment")
@Validated
public class ProjectCategoryAttachmentController {

    @Resource
    private ProjectCategoryAttachmentService projectCategoryAttachmentService;

    @Resource
    private ProjectCategoryAttachmentMapper projectCategoryAttachmentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目实验名目附件")
    @PreAuthorize("@ss.hasPermission('jl:project-category-attachment:create')")
    public CommonResult<Long> createProjectCategoryAttachment(@Valid @RequestBody ProjectCategoryAttachmentCreateReqVO createReqVO) {
        return success(projectCategoryAttachmentService.createProjectCategoryAttachment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目实验名目附件")
    @PreAuthorize("@ss.hasPermission('jl:project-category-attachment:update')")
    public CommonResult<Boolean> updateProjectCategoryAttachment(@Valid @RequestBody ProjectCategoryAttachmentUpdateReqVO updateReqVO) {
        projectCategoryAttachmentService.updateProjectCategoryAttachment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目实验名目附件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-category-attachment:delete')")
    public CommonResult<Boolean> deleteProjectCategoryAttachment(@RequestParam("id") Long id) {
        projectCategoryAttachmentService.deleteProjectCategoryAttachment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目实验名目附件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-category-attachment:query')")
    public CommonResult<ProjectCategoryAttachmentRespVO> getProjectCategoryAttachment(@RequestParam("id") Long id) {
            Optional<ProjectCategoryAttachment> projectCategoryAttachment = projectCategoryAttachmentService.getProjectCategoryAttachment(id);
        return success(projectCategoryAttachment.map(projectCategoryAttachmentMapper::toDto).orElseThrow(() -> exception(PROJECT_CATEGORY_ATTACHMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目实验名目附件列表")
    @PreAuthorize("@ss.hasPermission('jl:project-category-attachment:query')")
    public CommonResult<PageResult<ProjectCategoryAttachmentRespVO>> getProjectCategoryAttachmentPage(@Valid ProjectCategoryAttachmentPageReqVO pageVO, @Valid ProjectCategoryAttachmentPageOrder orderV0) {
        PageResult<ProjectCategoryAttachment> pageResult = projectCategoryAttachmentService.getProjectCategoryAttachmentPage(pageVO, orderV0);
        return success(projectCategoryAttachmentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目实验名目附件 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-category-attachment:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectCategoryAttachmentExcel(@Valid ProjectCategoryAttachmentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectCategoryAttachment> list = projectCategoryAttachmentService.getProjectCategoryAttachmentList(exportReqVO);
        // 导出 Excel
        List<ProjectCategoryAttachmentExcelVO> excelData = projectCategoryAttachmentMapper.toExcelList(list);
        ExcelUtils.write(response, "项目实验名目附件.xls", "数据", ProjectCategoryAttachmentExcelVO.class, excelData);
    }

}

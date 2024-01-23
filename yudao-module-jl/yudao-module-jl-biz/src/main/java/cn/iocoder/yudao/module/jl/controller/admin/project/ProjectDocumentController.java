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
import cn.iocoder.yudao.module.jl.entity.project.ProjectDocument;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectDocumentMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectDocumentService;

@Tag(name = "管理后台 - 项目文档")
@RestController
@RequestMapping("/jl/project-document")
@Validated
public class ProjectDocumentController {

    @Resource
    private ProjectDocumentService projectDocumentService;

    @Resource
    private ProjectDocumentMapper projectDocumentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目文档")
    @PreAuthorize("@ss.hasPermission('jl:project-document:create')")
    public CommonResult<Long> createProjectDocument(@Valid @RequestBody ProjectDocumentCreateReqVO createReqVO) {
        return success(projectDocumentService.createProjectDocument(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目文档")
    @PreAuthorize("@ss.hasPermission('jl:project-document:update')")
    public CommonResult<Boolean> updateProjectDocument(@Valid @RequestBody ProjectDocumentUpdateReqVO updateReqVO) {
        projectDocumentService.updateProjectDocument(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目文档")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-document:delete')")
    public CommonResult<Boolean> deleteProjectDocument(@RequestParam("id") Long id) {
        projectDocumentService.deleteProjectDocument(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目文档")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-document:query')")
    public CommonResult<ProjectDocumentRespVO> getProjectDocument(@RequestParam("id") Long id) {
            Optional<ProjectDocument> projectDocument = projectDocumentService.getProjectDocument(id);
        return success(projectDocument.map(projectDocumentMapper::toDto).orElseThrow(() -> exception(PROJECT_DOCUMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目文档列表")
    @PreAuthorize("@ss.hasPermission('jl:project-document:query')")
    public CommonResult<PageResult<ProjectDocumentRespVO>> getProjectDocumentPage(@Valid ProjectDocumentPageReqVO pageVO, @Valid ProjectDocumentPageOrder orderV0) {
        PageResult<ProjectDocument> pageResult = projectDocumentService.getProjectDocumentPage(pageVO, orderV0);
        return success(projectDocumentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目文档 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-document:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectDocumentExcel(@Valid ProjectDocumentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectDocument> list = projectDocumentService.getProjectDocumentList(exportReqVO);
        // 导出 Excel
        List<ProjectDocumentExcelVO> excelData = projectDocumentMapper.toExcelList(list);
        ExcelUtils.write(response, "项目文档.xls", "数据", ProjectDocumentExcelVO.class, excelData);
    }

}

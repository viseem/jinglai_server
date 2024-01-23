package cn.iocoder.yudao.module.jl.controller.admin.projectquotation;

import cn.iocoder.yudao.framework.excel.core.util.excelhandler.CustomExcelCellWriterHandler;
import cn.iocoder.yudao.framework.excel.core.util.excelstrategy.JLCustomMergeStrategy;
import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
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

import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.mapper.projectquotation.ProjectQuotationMapper;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationService;

@Tag(name = "管理后台 - 项目报价")
@RestController
@RequestMapping("/jl/project-quotation")
@Validated
public class ProjectQuotationController {

    @Resource
    private ProjectQuotationService projectQuotationService;

    @Resource
    private ProjectQuotationMapper projectQuotationMapper;

    @Resource
    private ProjectRepository projectRepository;


    @PostMapping("/create")
    @Operation(summary = "创建项目报价")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:create')")
    public CommonResult<Long> createProjectQuotation(@Valid @RequestBody ProjectQuotationCreateReqVO createReqVO) {
        return success(projectQuotationService.createProjectQuotation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目报价")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:update')")
    public CommonResult<Boolean> updateProjectQuotation(@Valid @RequestBody ProjectQuotationUpdateReqVO updateReqVO) {
        projectQuotationService.updateProjectQuotation(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-discount")
    @Operation(summary = "更新项目报价折扣")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:update')")
    public CommonResult<Boolean> updateProjectQuotationDiscount(@Valid @RequestBody ProjectQuotationNoRequireVO updateReqVO) {
        projectQuotationService.updateProjectQuotationDiscount(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-version")
    @Operation(summary = "更新项目报价")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:update')")
    public CommonResult<Boolean> updateProjectQuotationVersion(@Valid @RequestBody ProjectQuotationUpdateReqVO updateReqVO) {
        projectRepository.updateCurrentQuotationIdById(updateReqVO.getId(),updateReqVO.getProjectId());
        return success(true);
    }

    @PutMapping("/save")
    @Operation(summary = "这个是保存、存为新版本按钮")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:update')")
    public CommonResult<Boolean> saveProjectQuotation(@Valid @RequestBody ProjectQuotationSaveReqVO updateReqVO) {
        projectQuotationService.saveProjectQuotation(updateReqVO);
        return success(true);
    }

    @PutMapping("/save-plan")
    @Operation(summary = "专门保存方案")
    @OperateLog(enable = false)
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:update')")
    public CommonResult<Long> saveProjectQuotationPlan(@Valid @RequestBody ProjectQuotationUpdatePlanReqVO updateReqVO) {
        projectQuotationService.updateProjectQuotationPlan(updateReqVO);
        return success(updateReqVO.getId());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目报价")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:delete')")
    public CommonResult<Boolean> deleteProjectQuotation(@RequestParam("id") Long id) {
        projectQuotationService.deleteProjectQuotation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目报价")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:query')")
    public CommonResult<ProjectQuotationRespVO> getProjectQuotation(@RequestParam("id") Long id) {
            Optional<ProjectQuotation> projectQuotation = projectQuotationService.getProjectQuotation(id);
        return success(projectQuotation.map(projectQuotationMapper::toDto).orElseThrow(() -> exception(PROJECT_QUOTATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目报价列表")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:query')")
    public CommonResult<PageResult<ProjectQuotationRespVO>> getProjectQuotationPage(@Valid ProjectQuotationPageReqVO pageVO, @Valid ProjectQuotationPageOrder orderV0) {
        PageResult<ProjectQuotation> pageResult = projectQuotationService.getProjectQuotationPage(pageVO, orderV0);
        return success(projectQuotationMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目报价 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-quotation:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectQuotationExcel(@Valid ProjectQuotationExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        ProjectQuotationExportRespVO resp = projectQuotationService.getProjectQuotationList(exportReqVO);
        // 导出 Excel
        List<ProjectQuotationExcelVO> excelData = projectQuotationMapper.toExcelList2(resp.getQuotationItems());
        ExcelUtils.write(response, "项目报价.xls", "数据", ProjectQuotationExcelVO.class, excelData, new JLCustomMergeStrategy(resp.getSupplyCount(),resp.getChargeCount()),new CustomExcelCellWriterHandler(resp.getSupplyCount(),resp.getChargeCount()));
    }
}

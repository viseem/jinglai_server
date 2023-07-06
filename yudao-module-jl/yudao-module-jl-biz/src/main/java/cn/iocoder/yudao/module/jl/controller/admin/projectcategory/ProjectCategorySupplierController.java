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
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategorySupplier;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategorySupplierMapper;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategorySupplierService;

@Tag(name = "管理后台 - 项目实验委外供应商")
@RestController
@RequestMapping("/jl/project-category-supplier")
@Validated
public class ProjectCategorySupplierController {

    @Resource
    private ProjectCategorySupplierService projectCategorySupplierService;

    @Resource
    private ProjectCategorySupplierMapper projectCategorySupplierMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目实验委外供应商")
    @PreAuthorize("@ss.hasPermission('jl:project-category-supplier:create')")
    public CommonResult<Long> createProjectCategorySupplier(@Valid @RequestBody ProjectCategorySupplierCreateReqVO createReqVO) {
        return success(projectCategorySupplierService.createProjectCategorySupplier(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目实验委外供应商")
    @PreAuthorize("@ss.hasPermission('jl:project-category-supplier:update')")
    public CommonResult<Boolean> updateProjectCategorySupplier(@Valid @RequestBody ProjectCategorySupplierUpdateReqVO updateReqVO) {
        projectCategorySupplierService.updateProjectCategorySupplier(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目实验委外供应商")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-category-supplier:delete')")
    public CommonResult<Boolean> deleteProjectCategorySupplier(@RequestParam("id") Long id) {
        projectCategorySupplierService.deleteProjectCategorySupplier(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目实验委外供应商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-category-supplier:query')")
    public CommonResult<ProjectCategorySupplierRespVO> getProjectCategorySupplier(@RequestParam("id") Long id) {
            Optional<ProjectCategorySupplier> projectCategorySupplier = projectCategorySupplierService.getProjectCategorySupplier(id);
        return success(projectCategorySupplier.map(projectCategorySupplierMapper::toDto).orElseThrow(() -> exception(PROJECT_CATEGORY_SUPPLIER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目实验委外供应商列表")
    @PreAuthorize("@ss.hasPermission('jl:project-category-supplier:query')")
    public CommonResult<PageResult<ProjectCategorySupplierRespVO>> getProjectCategorySupplierPage(@Valid ProjectCategorySupplierPageReqVO pageVO, @Valid ProjectCategorySupplierPageOrder orderV0) {
        PageResult<ProjectCategorySupplier> pageResult = projectCategorySupplierService.getProjectCategorySupplierPage(pageVO, orderV0);
        return success(projectCategorySupplierMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目实验委外供应商 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-category-supplier:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectCategorySupplierExcel(@Valid ProjectCategorySupplierExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectCategorySupplier> list = projectCategorySupplierService.getProjectCategorySupplierList(exportReqVO);
        // 导出 Excel
        List<ProjectCategorySupplierExcelVO> excelData = projectCategorySupplierMapper.toExcelList(list);
        ExcelUtils.write(response, "项目实验委外供应商.xls", "数据", ProjectCategorySupplierExcelVO.class, excelData);
    }

}

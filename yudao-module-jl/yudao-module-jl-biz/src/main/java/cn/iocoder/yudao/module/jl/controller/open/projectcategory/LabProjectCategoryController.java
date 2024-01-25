package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo.LabExpSopUpdateReqVO;
import cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo.LabExpUpdateReqVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategorySimple;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectCategoryMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_CATEGORY_NOT_EXISTS;

@Tag(name = "管理后台 - 项目的实验名目")
@RestController
@RequestMapping("/lab/project-category")
@Validated
public class LabProjectCategoryController {

    @Resource
    private ProjectCategoryService projectCategoryService;

    @Resource
    private ProjectCategoryMapper projectCategoryMapper;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @GetMapping("/page")
    @PermitAll
    @Operation(summary = "(分页)获得项目的实验名目列表")
    public CommonResult<PageResult<ProjectCategorySimple>> getProjectCategoryPage(@Valid ProjectCategoryPageReqVO pageVO, @Valid ProjectCategoryPageOrder orderV0) {
//        pageVO.setPageNo(-1);
        PageResult<ProjectCategorySimple> pageResult = projectCategoryService.getProjectCategoryPageSimple(pageVO, orderV0);
        return success(pageResult);
    }

    @PostMapping("/update-stage")
    @Operation(summary = "更新")
    @PreAuthorize("@ss.hasPermission('jl:project-category:update')")
    public CommonResult<Boolean> updateProjectSop(@Valid @RequestBody LabExpUpdateReqVO updateReqVO) {
        projectCategoryRepository.updateStageById(updateReqVO.getStage(),updateReqVO.getId());
        return success(true);
    }

    @GetMapping("/get")
    @PermitAll
    @Operation(summary = "通过 ID 获得项目的实验名目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProjectCategoryRespVO> getProjectCategory(@RequestParam("id") Long id) {
        Optional<ProjectCategory> projectCategory = projectCategoryService.getProjectCategory(id);
        return success(projectCategory.map(projectCategoryMapper::toDto).orElseThrow(() -> exception(PROJECT_CATEGORY_NOT_EXISTS)));
    }
    @GetMapping("/export-excel")
    @Operation(summary = "导出项目的实验名目 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-category:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectCategoryExcel(@Valid ProjectCategoryExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectCategory> list = projectCategoryService.getProjectCategoryList(exportReqVO);
        // 导出 Excel
        List<ProjectCategoryExcelVO> excelData = projectCategoryMapper.toExcelList(list);
        ExcelUtils.write(response, "项目的实验名目.xls", "数据", ProjectCategoryExcelVO.class, excelData);
    }

}

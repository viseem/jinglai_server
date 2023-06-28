package cn.iocoder.yudao.module.jl.controller.admin.project;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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
import cn.iocoder.yudao.module.jl.entity.project.ProjectSop;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectSopMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectSopService;

@Tag(name = "管理后台 - 项目中的实验名目的操作SOP")
@RestController
@RequestMapping("/jl/project-sop")
@Validated
public class ProjectSopController {

    @Resource
    private ProjectSopService projectSopService;

    @Resource
    private ProjectSopMapper projectSopMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目中的实验名目的操作SOP")
    @PreAuthorize("@ss.hasPermission('jl:project-sop:create')")
    public CommonResult<Long> createProjectSop(@Valid @RequestBody ProjectSopCreateReqVO createReqVO) {
        return success(projectSopService.createProjectSop(createReqVO));
    }

    //TODO 变更状态时候用，写个batch update最好，根据id更新status
    @PutMapping("/save")
    @Operation(summary = "批量更新sop")
    @PreAuthorize("@ss.hasPermission('jl:project-sop:update')")
    public CommonResult<Boolean> saveProjectSop(@Valid @RequestBody List<ProjectSopBaseVO> sopList) {
        projectSopService.saveProjectSop(sopList);
        return success(true);
    }

    // todo 未完
    @PutMapping("/batch-update-status")
    @Operation(summary = "批量更新sop status")
    @PreAuthorize("@ss.hasPermission('jl:project-sop:update')")
    public CommonResult<Boolean> batchUpdateSopStatus(@Valid @RequestBody List<ProjectSopBaseVO> sopList) {
        projectSopService.batchUpdateSopStatus(sopList);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目中的实验名目的操作SOP")
    @PreAuthorize("@ss.hasPermission('jl:project-sop:update')")
    public CommonResult<Boolean> updateProjectSop(@Valid @RequestBody ProjectSopUpdateReqVO updateReqVO) {
        projectSopService.updateProjectSop(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目中的实验名目的操作SOP")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-sop:delete')")
    public CommonResult<Boolean> deleteProjectSop(@RequestParam("id") Long id) {
        projectSopService.deleteProjectSop(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目中的实验名目的操作SOP")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-sop:query')")
    public CommonResult<ProjectSopRespVO> getProjectSop(@RequestParam("id") Long id) {
            Optional<ProjectSop> projectSop = projectSopService.getProjectSop(id);
        return success(projectSop.map(projectSopMapper::toDto).orElseThrow(() -> exception(PROJECT_SOP_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目中的实验名目的操作SOP列表")
    @PreAuthorize("@ss.hasPermission('jl:project-sop:query')")
    public CommonResult<PageResult<ProjectSopRespVO>> getProjectSopPage(@Valid ProjectSopPageReqVO pageVO, @Valid ProjectSopPageOrder orderV0) {
        PageResult<ProjectSop> pageResult = projectSopService.getProjectSopPage(pageVO, orderV0);
        return success(projectSopMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目中的实验名目的操作SOP Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-sop:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectSopExcel(@Valid ProjectSopExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectSop> list = projectSopService.getProjectSopList(exportReqVO);
        // 导出 Excel
        List<ProjectSopExcelVO> excelData = projectSopMapper.toExcelList(list);
        ExcelUtils.write(response, "项目中的实验名目的操作SOP.xls", "数据", ProjectSopExcelVO.class, excelData);
    }

}

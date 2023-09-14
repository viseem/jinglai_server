package cn.iocoder.yudao.module.jl.controller.admin.projectfeedback;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfeedback.ProjectFeedbackFocus;
import cn.iocoder.yudao.module.jl.mapper.projectfeedback.ProjectFeedbackFocusMapper;
import cn.iocoder.yudao.module.jl.service.projectfeedback.ProjectFeedbackFocusService;

@Tag(name = "管理后台 - 晶莱项目反馈关注人员")
@RestController
@RequestMapping("/jl/project-feedback-focus")
@Validated
public class ProjectFeedbackFocusController {

    @Resource
    private ProjectFeedbackFocusService projectFeedbackFocusService;

    @Resource
    private ProjectFeedbackFocusMapper projectFeedbackFocusMapper;

    @PostMapping("/create")
    @Operation(summary = "创建晶莱项目反馈关注人员")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback-focus:create')")
    public CommonResult<Long> createProjectFeedbackFocus(@Valid @RequestBody ProjectFeedbackFocusCreateReqVO createReqVO) {
        return success(projectFeedbackFocusService.createProjectFeedbackFocus(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新晶莱项目反馈关注人员")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback-focus:update')")
    public CommonResult<Boolean> updateProjectFeedbackFocus(@Valid @RequestBody ProjectFeedbackFocusUpdateReqVO updateReqVO) {
        projectFeedbackFocusService.updateProjectFeedbackFocus(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除晶莱项目反馈关注人员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-feedback-focus:delete')")
    public CommonResult<Boolean> deleteProjectFeedbackFocus(@RequestParam("id") Long id) {
        projectFeedbackFocusService.deleteProjectFeedbackFocus(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得晶莱项目反馈关注人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback-focus:query')")
    public CommonResult<ProjectFeedbackFocusRespVO> getProjectFeedbackFocus(@RequestParam("id") Long id) {
            Optional<ProjectFeedbackFocus> projectFeedbackFocus = projectFeedbackFocusService.getProjectFeedbackFocus(id);
        return success(projectFeedbackFocus.map(projectFeedbackFocusMapper::toDto).orElseThrow(() -> exception(PROJECT_FEEDBACK_FOCUS_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得晶莱项目反馈关注人员列表")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback-focus:query')")
    public CommonResult<PageResult<ProjectFeedbackFocusRespVO>> getProjectFeedbackFocusPage(@Valid ProjectFeedbackFocusPageReqVO pageVO, @Valid ProjectFeedbackFocusPageOrder orderV0) {
        PageResult<ProjectFeedbackFocus> pageResult = projectFeedbackFocusService.getProjectFeedbackFocusPage(pageVO, orderV0);
        return success(projectFeedbackFocusMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出晶莱项目反馈关注人员 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback-focus:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectFeedbackFocusExcel(@Valid ProjectFeedbackFocusExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectFeedbackFocus> list = projectFeedbackFocusService.getProjectFeedbackFocusList(exportReqVO);
        // 导出 Excel
        List<ProjectFeedbackFocusExcelVO> excelData = projectFeedbackFocusMapper.toExcelList(list);
        ExcelUtils.write(response, "晶莱项目反馈关注人员.xls", "数据", ProjectFeedbackFocusExcelVO.class, excelData);
    }

}

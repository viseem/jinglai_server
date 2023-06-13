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
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectFeedbackMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectFeedbackService;

@Tag(name = "管理后台 - 项目反馈")
@RestController
@RequestMapping("/jl/project-feedback")
@Validated
public class ProjectFeedbackController {

    @Resource
    private ProjectFeedbackService projectFeedbackService;

    @Resource
    private ProjectFeedbackMapper projectFeedbackMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目反馈")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback:create')")
    public CommonResult<Long> createProjectFeedback(@Valid @RequestBody ProjectFeedbackCreateReqVO createReqVO) {
        return success(projectFeedbackService.createProjectFeedback(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目反馈")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback:update')")
    public CommonResult<Boolean> updateProjectFeedback(@Valid @RequestBody ProjectFeedbackUpdateReqVO updateReqVO) {
        projectFeedbackService.updateProjectFeedback(updateReqVO);
        return success(true);
    }

    @PutMapping("/reply")
    @Operation(summary = "处理项目反馈")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback:update')")
    public CommonResult<Boolean> updateProjectFeedback(@Valid @RequestBody ProjectFeedbackReplyReqVO replyReqVO) {
        Long userId = getLoginUserId();
        replyReqVO.setResultUserId(userId);
        projectFeedbackService.replyFeedback(replyReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目反馈")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-feedback:delete')")
    public CommonResult<Boolean> deleteProjectFeedback(@RequestParam("id") Long id) {
        projectFeedbackService.deleteProjectFeedback(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目反馈")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback:query')")
    public CommonResult<ProjectFeedbackRespVO> getProjectFeedback(@RequestParam("id") Long id) {
            Optional<ProjectFeedback> projectFeedback = projectFeedbackService.getProjectFeedback(id);
        return success(projectFeedback.map(projectFeedbackMapper::toDto).orElseThrow(() -> exception(PROJECT_FEEDBACK_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目反馈列表")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback:query')")
    public CommonResult<PageResult<ProjectFeedbackRespVO>> getProjectFeedbackPage(@Valid ProjectFeedbackPageReqVO pageVO, @Valid ProjectFeedbackPageOrder orderV0) {
        PageResult<ProjectFeedback> pageResult = projectFeedbackService.getProjectFeedbackPage(pageVO, orderV0);
        return success(projectFeedbackMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目反馈 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-feedback:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectFeedbackExcel(@Valid ProjectFeedbackExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectFeedback> list = projectFeedbackService.getProjectFeedbackList(exportReqVO);
        // 导出 Excel
        List<ProjectFeedbackExcelVO> excelData = projectFeedbackMapper.toExcelList(list);
        ExcelUtils.write(response, "项目反馈.xls", "数据", ProjectFeedbackExcelVO.class, excelData);
    }

}

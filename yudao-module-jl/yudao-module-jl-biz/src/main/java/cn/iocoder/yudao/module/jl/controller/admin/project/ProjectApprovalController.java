package cn.iocoder.yudao.module.jl.controller.admin.project;

import cn.iocoder.yudao.module.jl.utils.NeedAuditHandler;
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
import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectApprovalMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectApprovalService;

@Tag(name = "管理后台 - 项目的状态变更记录")
@RestController
@RequestMapping("/jl/project-approval")
@Validated
public class ProjectApprovalController {

    @Resource
    private ProjectApprovalService projectApprovalService;

    @Resource
    private ProjectApprovalMapper projectApprovalMapper;

    @Resource
    private NeedAuditHandler needAuditHandler;

    @PostMapping("/create")
    @Operation(summary = "创建项目的状态变更记录")
    @PreAuthorize("@ss.hasPermission('jl:project-approval:create')")
    public CommonResult<Long> createProjectApproval(@Valid @RequestBody ProjectApprovalCreateReqVO createReqVO,HttpServletRequest request) {
        createReqVO.setNeedAudit(needAuditHandler.needAudit(request,createReqVO.getStage()));
        return success(projectApprovalService.createProjectApproval(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目的状态变更记录")
    @PreAuthorize("@ss.hasPermission('jl:project-approval:update')")
    public CommonResult<Boolean> updateProjectApproval(@Valid @RequestBody ProjectApprovalUpdateReqVO updateReqVO) {
        projectApprovalService.updateProjectApproval(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目的状态变更记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-approval:delete')")
    public CommonResult<Boolean> deleteProjectApproval(@RequestParam("id") Long id) {
        projectApprovalService.deleteProjectApproval(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目的状态变更记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-approval:query')")
    public CommonResult<ProjectApprovalRespVO> getProjectApproval(@RequestParam("id") Long id) {
            Optional<ProjectApproval> projectApproval = projectApprovalService.getProjectApproval(id);
        return success(projectApproval.map(projectApprovalMapper::toDto).orElseThrow(() -> exception(PROJECT_APPROVAL_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目的状态变更记录列表")
    @PreAuthorize("@ss.hasPermission('jl:project-approval:query')")
    public CommonResult<PageResult<ProjectApprovalRespVO>> getProjectApprovalPage(@Valid ProjectApprovalPageReqVO pageVO, @Valid ProjectApprovalPageOrder orderV0) {
        PageResult<ProjectApproval> pageResult = projectApprovalService.getProjectApprovalPage(pageVO, orderV0);
        return success(projectApprovalMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目的状态变更记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-approval:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectApprovalExcel(@Valid ProjectApprovalExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectApproval> list = projectApprovalService.getProjectApprovalList(exportReqVO);
        // 导出 Excel
        List<ProjectApprovalExcelVO> excelData = projectApprovalMapper.toExcelList(list);
        ExcelUtils.write(response, "项目的状态变更记录.xls", "数据", ProjectApprovalExcelVO.class, excelData);
    }

}

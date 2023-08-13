package cn.iocoder.yudao.module.jl.controller.admin.approval;

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

import cn.iocoder.yudao.module.jl.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.jl.entity.approval.ApprovalProgress;
import cn.iocoder.yudao.module.jl.mapper.approval.ApprovalProgressMapper;
import cn.iocoder.yudao.module.jl.service.approval.ApprovalProgressService;

@Tag(name = "管理后台 - 审批流程")
@RestController
@RequestMapping("/jl/approval-progress")
@Validated
public class ApprovalProgressController {

    @Resource
    private ApprovalProgressService approvalProgressService;

    @Resource
    private ApprovalProgressMapper approvalProgressMapper;

    @PostMapping("/create")
    @Operation(summary = "创建审批流程")
    @PreAuthorize("@ss.hasPermission('jl:approval-progress:create')")
    public CommonResult<Long> createApprovalProgress(@Valid @RequestBody ApprovalProgressCreateReqVO createReqVO) {
        return success(approvalProgressService.createApprovalProgress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新审批流程")
    @PreAuthorize("@ss.hasPermission('jl:approval-progress:update')")
    public CommonResult<Boolean> updateApprovalProgress(@Valid @RequestBody ApprovalProgressUpdateReqVO updateReqVO) {
        approvalProgressService.updateApprovalProgress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除审批流程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:approval-progress:delete')")
    public CommonResult<Boolean> deleteApprovalProgress(@RequestParam("id") Long id) {
        approvalProgressService.deleteApprovalProgress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得审批流程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:approval-progress:query')")
    public CommonResult<ApprovalProgressRespVO> getApprovalProgress(@RequestParam("id") Long id) {
            Optional<ApprovalProgress> approvalProgress = approvalProgressService.getApprovalProgress(id);
        return success(approvalProgress.map(approvalProgressMapper::toDto).orElseThrow(() -> exception(APPROVAL_PROGRESS_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得审批流程列表")
    @PreAuthorize("@ss.hasPermission('jl:approval-progress:query')")
    public CommonResult<PageResult<ApprovalProgressRespVO>> getApprovalProgressPage(@Valid ApprovalProgressPageReqVO pageVO, @Valid ApprovalProgressPageOrder orderV0) {
        PageResult<ApprovalProgress> pageResult = approvalProgressService.getApprovalProgressPage(pageVO, orderV0);
        return success(approvalProgressMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出审批流程 Excel")
    @PreAuthorize("@ss.hasPermission('jl:approval-progress:export')")
    @OperateLog(type = EXPORT)
    public void exportApprovalProgressExcel(@Valid ApprovalProgressExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ApprovalProgress> list = approvalProgressService.getApprovalProgressList(exportReqVO);
        // 导出 Excel
        List<ApprovalProgressExcelVO> excelData = approvalProgressMapper.toExcelList(list);
        ExcelUtils.write(response, "审批流程.xls", "数据", ApprovalProgressExcelVO.class, excelData);
    }

}

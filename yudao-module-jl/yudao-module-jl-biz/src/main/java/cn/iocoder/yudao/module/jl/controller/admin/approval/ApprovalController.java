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
import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import cn.iocoder.yudao.module.jl.mapper.approval.ApprovalMapper;
import cn.iocoder.yudao.module.jl.service.approval.ApprovalService;

@Tag(name = "管理后台 - 审批")
@RestController
@RequestMapping("/jl/approval")
@Validated
public class ApprovalController {

    @Resource
    private ApprovalService approvalService;

    @Resource
    private ApprovalMapper approvalMapper;

    @PostMapping("/create")
    @Operation(summary = "创建审批")
    @PreAuthorize("@ss.hasPermission('jl:approval:create')")
    public CommonResult<Long> createApproval(@Valid @RequestBody ApprovalCreateReqVO createReqVO) {
        return success(approvalService.createApproval(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新审批")
    @PreAuthorize("@ss.hasPermission('jl:approval:update')")
    public CommonResult<Boolean> updateApproval(@Valid @RequestBody ApprovalUpdateReqVO updateReqVO) {
        approvalService.updateApproval(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除审批")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:approval:delete')")
    public CommonResult<Boolean> deleteApproval(@RequestParam("id") Long id) {
        approvalService.deleteApproval(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得审批")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:approval:query')")
    public CommonResult<ApprovalRespVO> getApproval(@RequestParam("id") Long id) {
            Optional<Approval> approval = approvalService.getApproval(id);
        return success(approval.map(approvalMapper::toDto).orElseThrow(() -> exception(APPROVAL_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得审批列表")
    @PreAuthorize("@ss.hasPermission('jl:approval:query')")
    public CommonResult<PageResult<ApprovalRespVO>> getApprovalPage(@Valid ApprovalPageReqVO pageVO, @Valid ApprovalPageOrder orderV0) {
        PageResult<Approval> pageResult = approvalService.getApprovalPage(pageVO, orderV0);
        return success(approvalMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出审批 Excel")
    @PreAuthorize("@ss.hasPermission('jl:approval:export')")
    @OperateLog(type = EXPORT)
    public void exportApprovalExcel(@Valid ApprovalExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Approval> list = approvalService.getApprovalList(exportReqVO);
        // 导出 Excel
        List<ApprovalExcelVO> excelData = approvalMapper.toExcelList(list);
        ExcelUtils.write(response, "审批.xls", "数据", ApprovalExcelVO.class, excelData);
    }

}

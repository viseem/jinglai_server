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
import cn.iocoder.yudao.module.jl.entity.project.SupplyLogAttachment;
import cn.iocoder.yudao.module.jl.mapper.project.SupplyLogAttachmentMapper;
import cn.iocoder.yudao.module.jl.service.project.SupplyLogAttachmentService;

@Tag(name = "管理后台 - 库管项目物资库存变更日志附件")
@RestController
@RequestMapping("/jl/supply-log-attachment")
@Validated
public class SupplyLogAttachmentController {

    @Resource
    private SupplyLogAttachmentService supplyLogAttachmentService;

    @Resource
    private SupplyLogAttachmentMapper supplyLogAttachmentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建库管项目物资库存变更日志附件")
    @PreAuthorize("@ss.hasPermission('jl:supply-log-attachment:create')")
    public CommonResult<Long> createSupplyLogAttachment(@Valid @RequestBody SupplyLogAttachmentCreateReqVO createReqVO) {
        return success(supplyLogAttachmentService.createSupplyLogAttachment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库管项目物资库存变更日志附件")
    @PreAuthorize("@ss.hasPermission('jl:supply-log-attachment:update')")
    public CommonResult<Boolean> updateSupplyLogAttachment(@Valid @RequestBody SupplyLogAttachmentUpdateReqVO updateReqVO) {
        supplyLogAttachmentService.updateSupplyLogAttachment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除库管项目物资库存变更日志附件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-log-attachment:delete')")
    public CommonResult<Boolean> deleteSupplyLogAttachment(@RequestParam("id") Long id) {
        supplyLogAttachmentService.deleteSupplyLogAttachment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得库管项目物资库存变更日志附件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-log-attachment:query')")
    public CommonResult<SupplyLogAttachmentRespVO> getSupplyLogAttachment(@RequestParam("id") Long id) {
            Optional<SupplyLogAttachment> supplyLogAttachment = supplyLogAttachmentService.getSupplyLogAttachment(id);
        return success(supplyLogAttachment.map(supplyLogAttachmentMapper::toDto).orElseThrow(() -> exception(SUPPLY_LOG_ATTACHMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得库管项目物资库存变更日志附件列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-log-attachment:query')")
    public CommonResult<PageResult<SupplyLogAttachmentRespVO>> getSupplyLogAttachmentPage(@Valid SupplyLogAttachmentPageReqVO pageVO, @Valid SupplyLogAttachmentPageOrder orderV0) {
        PageResult<SupplyLogAttachment> pageResult = supplyLogAttachmentService.getSupplyLogAttachmentPage(pageVO, orderV0);
        return success(supplyLogAttachmentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库管项目物资库存变更日志附件 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-log-attachment:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplyLogAttachmentExcel(@Valid SupplyLogAttachmentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplyLogAttachment> list = supplyLogAttachmentService.getSupplyLogAttachmentList(exportReqVO);
        // 导出 Excel
        List<SupplyLogAttachmentExcelVO> excelData = supplyLogAttachmentMapper.toExcelList(list);
        ExcelUtils.write(response, "库管项目物资库存变更日志附件.xls", "数据", SupplyLogAttachmentExcelVO.class, excelData);
    }

}

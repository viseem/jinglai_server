package cn.iocoder.yudao.module.jl.controller.admin.commonattachment;

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

import cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.mapper.commonattachment.CommonAttachmentMapper;
import cn.iocoder.yudao.module.jl.service.commonattachment.CommonAttachmentService;

@Tag(name = "管理后台 - 通用附件")
@RestController
@RequestMapping("/jl/common-attachment")
@Validated
public class CommonAttachmentController {

    @Resource
    private CommonAttachmentService commonAttachmentService;

    @Resource
    private CommonAttachmentMapper commonAttachmentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建通用附件")
    @PreAuthorize("@ss.hasPermission('jl:common-attachment:create')")
    public CommonResult<Long> createCommonAttachment(@Valid @RequestBody CommonAttachmentCreateReqVO createReqVO) {
        return success(commonAttachmentService.createCommonAttachment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通用附件")
    @PreAuthorize("@ss.hasPermission('jl:common-attachment:update')")
    public CommonResult<Boolean> updateCommonAttachment(@Valid @RequestBody CommonAttachmentUpdateReqVO updateReqVO) {
        commonAttachmentService.updateCommonAttachment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除通用附件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:common-attachment:delete')")
    public CommonResult<Boolean> deleteCommonAttachment(@RequestParam("id") Long id) {
        commonAttachmentService.deleteCommonAttachment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得通用附件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:common-attachment:query')")
    public CommonResult<CommonAttachmentRespVO> getCommonAttachment(@RequestParam("id") Long id) {
            Optional<CommonAttachment> commonAttachment = commonAttachmentService.getCommonAttachment(id);
        return success(commonAttachment.map(commonAttachmentMapper::toDto).orElseThrow(() -> exception(COMMON_ATTACHMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得通用附件列表")
    @PreAuthorize("@ss.hasPermission('jl:common-attachment:query')")
    public CommonResult<PageResult<CommonAttachmentRespVO>> getCommonAttachmentPage(@Valid CommonAttachmentPageReqVO pageVO, @Valid CommonAttachmentPageOrder orderV0) {
        PageResult<CommonAttachment> pageResult = commonAttachmentService.getCommonAttachmentPage(pageVO, orderV0);
        return success(commonAttachmentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出通用附件 Excel")
    @PreAuthorize("@ss.hasPermission('jl:common-attachment:export')")
    @OperateLog(type = EXPORT)
    public void exportCommonAttachmentExcel(@Valid CommonAttachmentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CommonAttachment> list = commonAttachmentService.getCommonAttachmentList(exportReqVO);
        // 导出 Excel
        List<CommonAttachmentExcelVO> excelData = commonAttachmentMapper.toExcelList(list);
        ExcelUtils.write(response, "通用附件.xls", "数据", CommonAttachmentExcelVO.class, excelData);
    }

}

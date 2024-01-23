package cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord;

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

import cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo.*;
import cn.iocoder.yudao.module.jl.entity.collaborationrecord.CollaborationRecord;
import cn.iocoder.yudao.module.jl.mapper.collaborationrecord.CollaborationRecordMapper;
import cn.iocoder.yudao.module.jl.service.collaborationrecord.CollaborationRecordService;

@Tag(name = "管理后台 - 通用协作记录")
@RestController
@RequestMapping("/jl/collaboration-record")
@Validated
public class CollaborationRecordController {

    @Resource
    private CollaborationRecordService collaborationRecordService;

    @Resource
    private CollaborationRecordMapper collaborationRecordMapper;

    @PostMapping("/create")
    @Operation(summary = "创建通用协作记录")
    @PreAuthorize("@ss.hasPermission('jl:collaboration-record:create')")
    public CommonResult<Long> createCollaborationRecord(@Valid @RequestBody CollaborationRecordCreateReqVO createReqVO) {
        return success(collaborationRecordService.createCollaborationRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通用协作记录")
    @PreAuthorize("@ss.hasPermission('jl:collaboration-record:update')")
    public CommonResult<Boolean> updateCollaborationRecord(@Valid @RequestBody CollaborationRecordUpdateReqVO updateReqVO) {
        collaborationRecordService.updateCollaborationRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除通用协作记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:collaboration-record:delete')")
    public CommonResult<Boolean> deleteCollaborationRecord(@RequestParam("id") Long id) {
        collaborationRecordService.deleteCollaborationRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得通用协作记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:collaboration-record:query')")
    public CommonResult<CollaborationRecordRespVO> getCollaborationRecord(@RequestParam("id") Long id) {
            Optional<CollaborationRecord> collaborationRecord = collaborationRecordService.getCollaborationRecord(id);
        return success(collaborationRecord.map(collaborationRecordMapper::toDto).orElseThrow(() -> exception(COLLABORATION_RECORD_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得通用协作记录列表")
    @PreAuthorize("@ss.hasPermission('jl:collaboration-record:query')")
    public CommonResult<PageResult<CollaborationRecordRespVO>> getCollaborationRecordPage(@Valid CollaborationRecordPageReqVO pageVO, @Valid CollaborationRecordPageOrder orderV0) {
        PageResult<CollaborationRecord> pageResult = collaborationRecordService.getCollaborationRecordPage(pageVO, orderV0);
        return success(collaborationRecordMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出通用协作记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:collaboration-record:export')")
    @OperateLog(type = EXPORT)
    public void exportCollaborationRecordExcel(@Valid CollaborationRecordExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CollaborationRecord> list = collaborationRecordService.getCollaborationRecordList(exportReqVO);
        // 导出 Excel
        List<CollaborationRecordExcelVO> excelData = collaborationRecordMapper.toExcelList(list);
        ExcelUtils.write(response, "通用协作记录.xls", "数据", CollaborationRecordExcelVO.class, excelData);
    }

}

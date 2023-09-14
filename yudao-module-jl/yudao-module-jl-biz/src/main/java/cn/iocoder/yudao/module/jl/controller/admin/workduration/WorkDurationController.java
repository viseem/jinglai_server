package cn.iocoder.yudao.module.jl.controller.admin.workduration;

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

import cn.iocoder.yudao.module.jl.controller.admin.workduration.vo.*;
import cn.iocoder.yudao.module.jl.entity.workduration.WorkDuration;
import cn.iocoder.yudao.module.jl.mapper.workduration.WorkDurationMapper;
import cn.iocoder.yudao.module.jl.service.workduration.WorkDurationService;

@Tag(name = "管理后台 - 工时")
@RestController
@RequestMapping("/jl/work-duration")
@Validated
public class WorkDurationController {

    @Resource
    private WorkDurationService workDurationService;

    @Resource
    private WorkDurationMapper workDurationMapper;

    @PostMapping("/create")
    @Operation(summary = "创建工时")
    @PreAuthorize("@ss.hasPermission('jl:work-duration:create')")
    public CommonResult<Long> createWorkDuration(@Valid @RequestBody WorkDurationCreateReqVO createReqVO) {
        return success(workDurationService.createWorkDuration(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工时")
    @PreAuthorize("@ss.hasPermission('jl:work-duration:update')")
    public CommonResult<Boolean> updateWorkDuration(@Valid @RequestBody WorkDurationUpdateReqVO updateReqVO) {
        workDurationService.updateWorkDuration(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除工时")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:work-duration:delete')")
    public CommonResult<Boolean> deleteWorkDuration(@RequestParam("id") Long id) {
        workDurationService.deleteWorkDuration(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得工时")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:work-duration:query')")
    public CommonResult<WorkDurationRespVO> getWorkDuration(@RequestParam("id") Long id) {
            Optional<WorkDuration> workDuration = workDurationService.getWorkDuration(id);
        return success(workDuration.map(workDurationMapper::toDto).orElseThrow(() -> exception(WORK_DURATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得工时列表")
    @PreAuthorize("@ss.hasPermission('jl:work-duration:query')")
    public CommonResult<PageResult<WorkDurationRespVO>> getWorkDurationPage(@Valid WorkDurationPageReqVO pageVO, @Valid WorkDurationPageOrder orderV0) {
        PageResult<WorkDuration> pageResult = workDurationService.getWorkDurationPage(pageVO, orderV0);
        return success(workDurationMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工时 Excel")
    @PreAuthorize("@ss.hasPermission('jl:work-duration:export')")
    @OperateLog(type = EXPORT)
    public void exportWorkDurationExcel(@Valid WorkDurationExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<WorkDuration> list = workDurationService.getWorkDurationList(exportReqVO);
        // 导出 Excel
        List<WorkDurationExcelVO> excelData = workDurationMapper.toExcelList(list);
        ExcelUtils.write(response, "工时.xls", "数据", WorkDurationExcelVO.class, excelData);
    }

}

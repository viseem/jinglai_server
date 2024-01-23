package cn.iocoder.yudao.module.jl.controller.admin.reminder;

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

import cn.iocoder.yudao.module.jl.controller.admin.reminder.vo.*;
import cn.iocoder.yudao.module.jl.entity.reminder.Reminder;
import cn.iocoder.yudao.module.jl.mapper.reminder.ReminderMapper;
import cn.iocoder.yudao.module.jl.service.reminder.ReminderService;

@Tag(name = "管理后台 - 晶莱提醒事项")
@RestController
@RequestMapping("/jl/reminder")
@Validated
public class ReminderController {

    @Resource
    private ReminderService reminderService;

    @Resource
    private ReminderMapper reminderMapper;

    @PostMapping("/create")
    @Operation(summary = "创建晶莱提醒事项")
    @PreAuthorize("@ss.hasPermission('jl:reminder:create')")
    public CommonResult<Long> createReminder(@Valid @RequestBody ReminderCreateReqVO createReqVO) {
        return success(reminderService.createReminder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新晶莱提醒事项")
    @PreAuthorize("@ss.hasPermission('jl:reminder:update')")
    public CommonResult<Boolean> updateReminder(@Valid @RequestBody ReminderUpdateReqVO updateReqVO) {
        reminderService.updateReminder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除晶莱提醒事项")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:reminder:delete')")
    public CommonResult<Boolean> deleteReminder(@RequestParam("id") Long id) {
        reminderService.deleteReminder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得晶莱提醒事项")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:reminder:query')")
    public CommonResult<ReminderRespVO> getReminder(@RequestParam("id") Long id) {
            Optional<Reminder> reminder = reminderService.getReminder(id);
        return success(reminder.map(reminderMapper::toDto).orElseThrow(() -> exception(REMINDER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得晶莱提醒事项列表")
    @PreAuthorize("@ss.hasPermission('jl:reminder:query')")
    public CommonResult<PageResult<ReminderRespVO>> getReminderPage(@Valid ReminderPageReqVO pageVO, @Valid ReminderPageOrder orderV0) {
        PageResult<Reminder> pageResult = reminderService.getReminderPage(pageVO, orderV0);
        return success(reminderMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出晶莱提醒事项 Excel")
    @PreAuthorize("@ss.hasPermission('jl:reminder:export')")
    @OperateLog(type = EXPORT)
    public void exportReminderExcel(@Valid ReminderExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Reminder> list = reminderService.getReminderList(exportReqVO);
        // 导出 Excel
        List<ReminderExcelVO> excelData = reminderMapper.toExcelList(list);
        ExcelUtils.write(response, "晶莱提醒事项.xls", "数据", ReminderExcelVO.class, excelData);
    }

}

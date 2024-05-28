package cn.iocoder.yudao.module.jl.controller.admin.commontask;

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

import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.module.jl.mapper.commontask.CommonTaskMapper;
import cn.iocoder.yudao.module.jl.service.commontask.CommonTaskService;

@Tag(name = "管理后台 - 通用任务")
@RestController
@RequestMapping("/jl/common-task")
@Validated
public class CommonTaskController {

    @Resource
    private CommonTaskService commonTaskService;

    @Resource
    private CommonTaskMapper commonTaskMapper;

    @PostMapping("/create")
    @Operation(summary = "创建通用任务")
    @PreAuthorize("@ss.hasPermission('jl:common-task:create')")
    public CommonResult<Long> createCommonTask(@Valid @RequestBody CommonTaskCreateReqVO createReqVO) {
        return success(commonTaskService.createCommonTask(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通用任务")
    @PreAuthorize("@ss.hasPermission('jl:common-task:update')")
    public CommonResult<Boolean> updateCommonTask(@Valid @RequestBody CommonTaskUpdateReqVO updateReqVO) {
        commonTaskService.updateCommonTask(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除通用任务")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:common-task:delete')")
    public CommonResult<Boolean> deleteCommonTask(@RequestParam("id") Long id) {
        commonTaskService.deleteCommonTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得通用任务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:common-task:query')")
    public CommonResult<CommonTaskRespVO> getCommonTask(@RequestParam("id") Long id) {
            Optional<CommonTask> commonTask = commonTaskService.getCommonTask(id);
        return success(commonTask.map(commonTaskMapper::toDto).orElseThrow(() -> exception(COMMON_TASK_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得通用任务列表")
    @PreAuthorize("@ss.hasPermission('jl:common-task:query')")
    public CommonResult<PageResult<CommonTaskRespVO>> getCommonTaskPage(@Valid CommonTaskPageReqVO pageVO, @Valid CommonTaskPageOrder orderV0) {
        PageResult<CommonTask> pageResult = commonTaskService.getCommonTaskPage(pageVO, orderV0);
        return success(commonTaskMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出通用任务 Excel")
    @PreAuthorize("@ss.hasPermission('jl:common-task:export')")
    @OperateLog(type = EXPORT)
    public void exportCommonTaskExcel(@Valid CommonTaskExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CommonTask> list = commonTaskService.getCommonTaskList(exportReqVO);
        // 导出 Excel
        List<CommonTaskExcelVO> excelData = commonTaskMapper.toExcelList(list);
        ExcelUtils.write(response, "通用任务.xls", "数据", CommonTaskExcelVO.class, excelData);
    }

}

package cn.iocoder.yudao.module.jl.controller.admin.taskrelation;

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

import cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskrelation.TaskRelation;
import cn.iocoder.yudao.module.jl.mapper.taskrelation.TaskRelationMapper;
import cn.iocoder.yudao.module.jl.service.taskrelation.TaskRelationService;

@Tag(name = "管理后台 - 任务关系依赖")
@RestController
@RequestMapping("/jl/task-relation")
@Validated
public class TaskRelationController {

    @Resource
    private TaskRelationService taskRelationService;

    @Resource
    private TaskRelationMapper taskRelationMapper;

    @PostMapping("/create")
    @Operation(summary = "创建任务关系依赖")
    @PreAuthorize("@ss.hasPermission('jl:task-relation:create')")
    public CommonResult<Long> createTaskRelation(@Valid @RequestBody TaskRelationCreateReqVO createReqVO) {
        return success(taskRelationService.createTaskRelation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新任务关系依赖")
    @PreAuthorize("@ss.hasPermission('jl:task-relation:update')")
    public CommonResult<Boolean> updateTaskRelation(@Valid @RequestBody TaskRelationUpdateReqVO updateReqVO) {
        taskRelationService.updateTaskRelation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除任务关系依赖")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:task-relation:delete')")
    public CommonResult<Boolean> deleteTaskRelation(@RequestParam("id") Long id) {
        taskRelationService.deleteTaskRelation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得任务关系依赖")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:task-relation:query')")
    public CommonResult<TaskRelationRespVO> getTaskRelation(@RequestParam("id") Long id) {
            Optional<TaskRelation> taskRelation = taskRelationService.getTaskRelation(id);
        return success(taskRelation.map(taskRelationMapper::toDto).orElseThrow(() -> exception(TASK_RELATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得任务关系依赖列表")
    @PreAuthorize("@ss.hasPermission('jl:task-relation:query')")
    public CommonResult<PageResult<TaskRelationRespVO>> getTaskRelationPage(@Valid TaskRelationPageReqVO pageVO, @Valid TaskRelationPageOrder orderV0) {
        PageResult<TaskRelation> pageResult = taskRelationService.getTaskRelationPage(pageVO, orderV0);
        return success(taskRelationMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出任务关系依赖 Excel")
    @PreAuthorize("@ss.hasPermission('jl:task-relation:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskRelationExcel(@Valid TaskRelationExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<TaskRelation> list = taskRelationService.getTaskRelationList(exportReqVO);
        // 导出 Excel
        List<TaskRelationExcelVO> excelData = taskRelationMapper.toExcelList(list);
        ExcelUtils.write(response, "任务关系依赖.xls", "数据", TaskRelationExcelVO.class, excelData);
    }

}

package cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation;

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

import cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import cn.iocoder.yudao.module.jl.mapper.taskarrangerelation.TaskArrangeRelationMapper;
import cn.iocoder.yudao.module.jl.service.taskarrangerelation.TaskArrangeRelationService;

@Tag(name = "管理后台 - 任务安排关系")
@RestController
@RequestMapping("/jl/task-arrange-relation")
@Validated
public class TaskArrangeRelationController {

    @Resource
    private TaskArrangeRelationService taskArrangeRelationService;

    @Resource
    private TaskArrangeRelationMapper taskArrangeRelationMapper;

    @PostMapping("/create")
    @Operation(summary = "创建任务安排关系")
    @PreAuthorize("@ss.hasPermission('jl:task-arrange-relation:create')")
    public CommonResult<Long> createTaskArrangeRelation(@Valid @RequestBody TaskArrangeRelationCreateReqVO createReqVO) {
        return success(taskArrangeRelationService.createTaskArrangeRelation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新任务安排关系")
    @PreAuthorize("@ss.hasPermission('jl:task-arrange-relation:update')")
    public CommonResult<Boolean> updateTaskArrangeRelation(@Valid @RequestBody TaskArrangeRelationUpdateReqVO updateReqVO) {
        taskArrangeRelationService.updateTaskArrangeRelation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除任务安排关系")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:task-arrange-relation:delete')")
    public CommonResult<Boolean> deleteTaskArrangeRelation(@RequestParam("id") Long id) {
        taskArrangeRelationService.deleteTaskArrangeRelation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得任务安排关系")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:task-arrange-relation:query')")
    public CommonResult<TaskArrangeRelationRespVO> getTaskArrangeRelation(@RequestParam("id") Long id) {
            Optional<TaskArrangeRelation> taskArrangeRelation = taskArrangeRelationService.getTaskArrangeRelation(id);
        return success(taskArrangeRelation.map(taskArrangeRelationMapper::toDto).orElseThrow(() -> exception(TASK_ARRANGE_RELATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得任务安排关系列表")
    @PreAuthorize("@ss.hasPermission('jl:task-arrange-relation:query')")
    public CommonResult<PageResult<TaskArrangeRelationRespVO>> getTaskArrangeRelationPage(@Valid TaskArrangeRelationPageReqVO pageVO, @Valid TaskArrangeRelationPageOrder orderV0) {
        PageResult<TaskArrangeRelation> pageResult = taskArrangeRelationService.getTaskArrangeRelationPage(pageVO, orderV0);
        return success(taskArrangeRelationMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出任务安排关系 Excel")
    @PreAuthorize("@ss.hasPermission('jl:task-arrange-relation:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskArrangeRelationExcel(@Valid TaskArrangeRelationExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<TaskArrangeRelation> list = taskArrangeRelationService.getTaskArrangeRelationList(exportReqVO);
        // 导出 Excel
        List<TaskArrangeRelationExcelVO> excelData = taskArrangeRelationMapper.toExcelList(list);
        ExcelUtils.write(response, "任务安排关系.xls", "数据", TaskArrangeRelationExcelVO.class, excelData);
    }

}

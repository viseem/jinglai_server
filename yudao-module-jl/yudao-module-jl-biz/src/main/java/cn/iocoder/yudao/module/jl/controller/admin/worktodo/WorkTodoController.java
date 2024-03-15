package cn.iocoder.yudao.module.jl.controller.admin.worktodo;

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

import cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodo.WorkTodo;
import cn.iocoder.yudao.module.jl.mapper.worktodo.WorkTodoMapper;
import cn.iocoder.yudao.module.jl.service.worktodo.WorkTodoService;

@Tag(name = "管理后台 - 工作任务 TODO")
@RestController
@RequestMapping("/jl/work-todo")
@Validated
public class WorkTodoController {

    @Resource
    private WorkTodoService workTodoService;

    @Resource
    private WorkTodoMapper workTodoMapper;

    @PostMapping("/create")
    @Operation(summary = "创建工作任务 TODO")
    public CommonResult<Long> createWorkTodo(@Valid @RequestBody WorkTodoCreateReqVO createReqVO) {
        return success(workTodoService.createWorkTodo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工作任务 TODO")
    public CommonResult<Boolean> updateWorkTodo(@Valid @RequestBody WorkTodoUpdateReqVO updateReqVO) {
        workTodoService.updateWorkTodo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除工作任务 TODO")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteWorkTodo(@RequestParam("id") Long id) {
        workTodoService.deleteWorkTodo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得工作任务 TODO")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<WorkTodoRespVO> getWorkTodo(@RequestParam("id") Long id) {
            Optional<WorkTodo> workTodo = workTodoService.getWorkTodo(id);
        return success(workTodo.map(workTodoMapper::toDto).orElseThrow(() -> exception(WORK_TODO_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得工作任务 TODO列表")
    public CommonResult<PageResult<WorkTodoRespVO>> getWorkTodoPage(@Valid WorkTodoPageReqVO pageVO, @Valid WorkTodoPageOrder orderV0) {
        PageResult<WorkTodo> pageResult = workTodoService.getWorkTodoPage(pageVO, orderV0);
        return success(workTodoMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工作任务 TODO Excel")
    @OperateLog(type = EXPORT)
    public void exportWorkTodoExcel(@Valid WorkTodoExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<WorkTodo> list = workTodoService.getWorkTodoList(exportReqVO);
        // 导出 Excel
        List<WorkTodoExcelVO> excelData = workTodoMapper.toExcelList(list);
        ExcelUtils.write(response, "工作任务 TODO.xls", "数据", WorkTodoExcelVO.class, excelData);
    }

}

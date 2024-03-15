package cn.iocoder.yudao.module.jl.controller.admin.worktodotag;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotag.WorkTodoTag;
import cn.iocoder.yudao.module.jl.mapper.worktodotag.WorkTodoTagMapper;
import cn.iocoder.yudao.module.jl.service.worktodotag.WorkTodoTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.WORK_TODO_TAG_NOT_EXISTS;

@Tag(name = "管理后台 - 工作任务 TODO 的标签")
@RestController
@RequestMapping("/jl/work-todo-tag")
@Validated
public class WorkTodoTagController {

    @Resource
    private WorkTodoTagService workTodoTagService;

    @Resource
    private WorkTodoTagMapper workTodoTagMapper;

    @PostMapping("/create")
    @Operation(summary = "创建工作任务 TODO 的标签")
    public CommonResult<Long> createWorkTodoTag(@Valid @RequestBody WorkTodoTagCreateReqVO createReqVO) {
        return success(workTodoTagService.createWorkTodoTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工作任务 TODO 的标签")
    public CommonResult<Boolean> updateWorkTodoTag(@Valid @RequestBody WorkTodoTagUpdateReqVO updateReqVO) {
        workTodoTagService.updateWorkTodoTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除工作任务 TODO 的标签")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteWorkTodoTag(@RequestParam("id") Long id) {
        workTodoTagService.deleteWorkTodoTag(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得工作任务 TODO 的标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<WorkTodoTagRespVO> getWorkTodoTag(@RequestParam("id") Long id) {
            Optional<WorkTodoTag> workTodoTag = workTodoTagService.getWorkTodoTag(id);
        return success(workTodoTag.map(workTodoTagMapper::toDto).orElseThrow(() -> exception(WORK_TODO_TAG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得工作任务 TODO 的标签列表")
    public CommonResult<PageResult<WorkTodoTagRespVO>> getWorkTodoTagPage(@Valid WorkTodoTagPageReqVO pageVO, @Valid WorkTodoTagPageOrder orderV0) {
        PageResult<WorkTodoTag> pageResult = workTodoTagService.getWorkTodoTagPage(pageVO, orderV0);
        return success(workTodoTagMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工作任务 TODO 的标签 Excel")
    @OperateLog(type = EXPORT)
    public void exportWorkTodoTagExcel(@Valid WorkTodoTagExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<WorkTodoTag> list = workTodoTagService.getWorkTodoTagList(exportReqVO);
        // 导出 Excel
        List<WorkTodoTagExcelVO> excelData = workTodoTagMapper.toExcelList(list);
        ExcelUtils.write(response, "工作任务 TODO 的标签.xls", "数据", WorkTodoTagExcelVO.class, excelData);
    }

}

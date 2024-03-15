package cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotagrelation.WorkTodoTagRelation;
import cn.iocoder.yudao.module.jl.mapper.worktodotagrelation.WorkTodoTagRelationMapper;
import cn.iocoder.yudao.module.jl.service.worktodotagrelation.WorkTodoTagRelationService;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.WORK_TODO_TAG_RELATION_NOT_EXISTS;

@Tag(name = "管理后台 - 工作任务 TODO 与标签的关联")
@RestController
@RequestMapping("/jl/work-todo-tag-relation")
@Validated
public class WorkTodoTagRelationController {

    @Resource
    private WorkTodoTagRelationService workTodoTagRelationService;

    @Resource
    private WorkTodoTagRelationMapper workTodoTagRelationMapper;

    @PostMapping("/create")
    @Operation(summary = "创建工作任务 TODO 与标签的关联")
    public CommonResult<Long> createWorkTodoTagRelation(@Valid @RequestBody WorkTodoTagRelationCreateReqVO createReqVO) {
        return success(workTodoTagRelationService.createWorkTodoTagRelation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新工作任务 TODO 与标签的关联")
    public CommonResult<Boolean> updateWorkTodoTagRelation(@Valid @RequestBody WorkTodoTagRelationUpdateReqVO updateReqVO) {
        workTodoTagRelationService.updateWorkTodoTagRelation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除工作任务 TODO 与标签的关联")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteWorkTodoTagRelation(@RequestParam("id") Long id) {
        workTodoTagRelationService.deleteWorkTodoTagRelation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得工作任务 TODO 与标签的关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<WorkTodoTagRelationRespVO> getWorkTodoTagRelation(@RequestParam("id") Long id) {
            Optional<WorkTodoTagRelation> workTodoTagRelation = workTodoTagRelationService.getWorkTodoTagRelation(id);
        return success(workTodoTagRelation.map(workTodoTagRelationMapper::toDto).orElseThrow(() -> exception(WORK_TODO_TAG_RELATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得工作任务 TODO 与标签的关联列表")
    public CommonResult<PageResult<WorkTodoTagRelationRespVO>> getWorkTodoTagRelationPage(@Valid WorkTodoTagRelationPageReqVO pageVO, @Valid WorkTodoTagRelationPageOrder orderV0) {
        PageResult<WorkTodoTagRelation> pageResult = workTodoTagRelationService.getWorkTodoTagRelationPage(pageVO, orderV0);
        return success(workTodoTagRelationMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工作任务 TODO 与标签的关联 Excel")
    @OperateLog(type = EXPORT)
    public void exportWorkTodoTagRelationExcel(@Valid WorkTodoTagRelationExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<WorkTodoTagRelation> list = workTodoTagRelationService.getWorkTodoTagRelationList(exportReqVO);
        // 导出 Excel
        List<WorkTodoTagRelationExcelVO> excelData = workTodoTagRelationMapper.toExcelList(list);
        ExcelUtils.write(response, "工作任务 TODO 与标签的关联.xls", "数据", WorkTodoTagRelationExcelVO.class, excelData);
    }

}

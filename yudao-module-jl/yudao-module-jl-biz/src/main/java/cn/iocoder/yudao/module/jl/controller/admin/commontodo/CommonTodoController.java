package cn.iocoder.yudao.module.jl.controller.admin.commontodo;

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

import cn.iocoder.yudao.module.jl.controller.admin.commontodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodo.CommonTodo;
import cn.iocoder.yudao.module.jl.mapper.commontodo.CommonTodoMapper;
import cn.iocoder.yudao.module.jl.service.commontodo.CommonTodoService;

@Tag(name = "管理后台 - 通用TODO")
@RestController
@RequestMapping("/jl/common-todo")
@Validated
public class CommonTodoController {

    @Resource
    private CommonTodoService commonTodoService;

    @Resource
    private CommonTodoMapper commonTodoMapper;

    @PostMapping("/create")
    @Operation(summary = "创建通用TODO")
    @PreAuthorize("@ss.hasPermission('jl:common-todo:create')")
    public CommonResult<Long> createCommonTodo(@Valid @RequestBody CommonTodoCreateReqVO createReqVO) {
        return success(commonTodoService.createCommonTodo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通用TODO")
    @PreAuthorize("@ss.hasPermission('jl:common-todo:update')")
    public CommonResult<Boolean> updateCommonTodo(@Valid @RequestBody CommonTodoUpdateReqVO updateReqVO) {
        commonTodoService.updateCommonTodo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除通用TODO")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:common-todo:delete')")
    public CommonResult<Boolean> deleteCommonTodo(@RequestParam("id") Long id) {
        commonTodoService.deleteCommonTodo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得通用TODO")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:common-todo:query')")
    public CommonResult<CommonTodoRespVO> getCommonTodo(@RequestParam("id") Long id) {
            Optional<CommonTodo> commonTodo = commonTodoService.getCommonTodo(id);
        return success(commonTodo.map(commonTodoMapper::toDto).orElseThrow(() -> exception(COMMON_TODO_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得通用TODO列表")
    @PreAuthorize("@ss.hasPermission('jl:common-todo:query')")
    public CommonResult<PageResult<CommonTodoRespVO>> getCommonTodoPage(@Valid CommonTodoPageReqVO pageVO, @Valid CommonTodoPageOrder orderV0) {
        PageResult<CommonTodo> pageResult = commonTodoService.getCommonTodoPage(pageVO, orderV0);
        return success(commonTodoMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出通用TODO Excel")
    @PreAuthorize("@ss.hasPermission('jl:common-todo:export')")
    @OperateLog(type = EXPORT)
    public void exportCommonTodoExcel(@Valid CommonTodoExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CommonTodo> list = commonTodoService.getCommonTodoList(exportReqVO);
        // 导出 Excel
        List<CommonTodoExcelVO> excelData = commonTodoMapper.toExcelList(list);
        ExcelUtils.write(response, "通用TODO.xls", "数据", CommonTodoExcelVO.class, excelData);
    }

}

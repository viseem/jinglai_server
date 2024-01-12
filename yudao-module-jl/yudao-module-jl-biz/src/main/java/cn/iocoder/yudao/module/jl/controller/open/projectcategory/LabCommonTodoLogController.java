package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodolog.CommonTodoLog;
import cn.iocoder.yudao.module.jl.mapper.commontodolog.CommonTodoLogMapper;
import cn.iocoder.yudao.module.jl.service.commontodolog.CommonTodoLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.COMMON_TODO_LOG_NOT_EXISTS;

@Tag(name = "管理后台 - 通用TODO记录")
@RestController
@RequestMapping("/lab/common-todo-log")
@Validated
public class LabCommonTodoLogController {

    @Resource
    private CommonTodoLogService commonTodoLogService;

    @Resource
    private CommonTodoLogMapper commonTodoLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建通用TODO记录")
    @PreAuthorize("@ss.hasPermission('jl:common-todo-log:create')")
    public CommonResult<Long> createCommonTodoLog(@Valid @RequestBody CommonTodoLogCreateReqVO createReqVO) {
        return success(commonTodoLogService.createCommonTodoLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通用TODO记录")
    @PreAuthorize("@ss.hasPermission('jl:common-todo-log:update')")
    public CommonResult<Boolean> updateCommonTodoLog(@Valid @RequestBody CommonTodoLogUpdateReqVO updateReqVO) {
        commonTodoLogService.updateCommonTodoLog(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新通用TODO记录状态")
    @PreAuthorize("@ss.hasPermission('jl:common-todo-log:update')")
    public CommonResult<Boolean> updateCommonTodoLogStatus(@Valid @RequestBody CommonTodoLogUpdateStatusReqVO updateReqVO) {
        commonTodoLogService.updateCommonTodoLogStatus(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除通用TODO记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:common-todo-log:delete')")
    public CommonResult<Boolean> deleteCommonTodoLog(@RequestParam("id") Long id) {
        commonTodoLogService.deleteCommonTodoLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得通用TODO记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:common-todo-log:query')")
    public CommonResult<CommonTodoLogRespVO> getCommonTodoLog(@RequestParam("id") Long id) {
            Optional<CommonTodoLog> commonTodoLog = commonTodoLogService.getCommonTodoLog(id);
        return success(commonTodoLog.map(commonTodoLogMapper::toDto).orElseThrow(() -> exception(COMMON_TODO_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得通用TODO记录列表")
    @PreAuthorize("@ss.hasPermission('jl:common-todo-log:query')")
    public CommonResult<PageResult<CommonTodoLogRespVO>> getCommonTodoLogPage(@Valid CommonTodoLogPageReqVO pageVO, @Valid CommonTodoLogPageOrder orderV0) {
        PageResult<CommonTodoLog> pageResult = commonTodoLogService.getCommonTodoLogPage(pageVO, orderV0);
        return success(commonTodoLogMapper.toPage(pageResult));
    }

}

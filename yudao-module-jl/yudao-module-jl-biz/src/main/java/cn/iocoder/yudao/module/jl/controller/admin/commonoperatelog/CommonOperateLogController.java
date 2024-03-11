package cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog;

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

import cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonoperatelog.CommonOperateLog;
import cn.iocoder.yudao.module.jl.mapper.commonoperatelog.CommonOperateLogMapper;
import cn.iocoder.yudao.module.jl.service.commonoperatelog.CommonOperateLogService;

@Tag(name = "管理后台 - 通用操作记录")
@RestController
@RequestMapping("/jl/common-operate-log")
@Validated
public class CommonOperateLogController {

    @Resource
    private CommonOperateLogService commonOperateLogService;

    @Resource
    private CommonOperateLogMapper commonOperateLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建通用操作记录")
    @PreAuthorize("@ss.hasPermission('jl:common-operate-log:create')")
    public CommonResult<Long> createCommonOperateLog(@Valid @RequestBody CommonOperateLogCreateReqVO createReqVO) {
        return success(commonOperateLogService.createCommonOperateLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新通用操作记录")
    @PreAuthorize("@ss.hasPermission('jl:common-operate-log:update')")
    public CommonResult<Boolean> updateCommonOperateLog(@Valid @RequestBody CommonOperateLogUpdateReqVO updateReqVO) {
        commonOperateLogService.updateCommonOperateLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除通用操作记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:common-operate-log:delete')")
    public CommonResult<Boolean> deleteCommonOperateLog(@RequestParam("id") Long id) {
        commonOperateLogService.deleteCommonOperateLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得通用操作记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:common-operate-log:query')")
    public CommonResult<CommonOperateLogRespVO> getCommonOperateLog(@RequestParam("id") Long id) {
            Optional<CommonOperateLog> commonOperateLog = commonOperateLogService.getCommonOperateLog(id);
        return success(commonOperateLog.map(commonOperateLogMapper::toDto).orElseThrow(() -> exception(COMMON_OPERATE_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得通用操作记录列表")
    @PreAuthorize("@ss.hasPermission('jl:common-operate-log:query')")
    public CommonResult<PageResult<CommonOperateLogRespVO>> getCommonOperateLogPage(@Valid CommonOperateLogPageReqVO pageVO, @Valid CommonOperateLogPageOrder orderV0) {
        PageResult<CommonOperateLog> pageResult = commonOperateLogService.getCommonOperateLogPage(pageVO, orderV0);
        return success(commonOperateLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出通用操作记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:common-operate-log:export')")
    @OperateLog(type = EXPORT)
    public void exportCommonOperateLogExcel(@Valid CommonOperateLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CommonOperateLog> list = commonOperateLogService.getCommonOperateLogList(exportReqVO);
        // 导出 Excel
        List<CommonOperateLogExcelVO> excelData = commonOperateLogMapper.toExcelList(list);
        ExcelUtils.write(response, "通用操作记录.xls", "数据", CommonOperateLogExcelVO.class, excelData);
    }

}

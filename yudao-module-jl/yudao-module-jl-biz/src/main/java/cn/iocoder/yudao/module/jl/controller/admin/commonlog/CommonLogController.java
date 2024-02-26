package cn.iocoder.yudao.module.jl.controller.admin.commonlog;

import cn.iocoder.yudao.framework.excel.core.util.excelhandler.CommonLogExcelCellWriterHandler;
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

import cn.iocoder.yudao.module.jl.controller.admin.commonlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonlog.CommonLog;
import cn.iocoder.yudao.module.jl.mapper.commonlog.CommonLogMapper;
import cn.iocoder.yudao.module.jl.service.commonlog.CommonLogService;

@Tag(name = "管理后台 - 管控记录")
@RestController
@RequestMapping("/jl/common-log")
@Validated
public class CommonLogController {

    @Resource
    private CommonLogService commonLogService;

    @Resource
    private CommonLogMapper commonLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建管控记录")
    @PreAuthorize("@ss.hasPermission('jl:common-log:create')")
    public CommonResult<Long> createCommonLog(@Valid @RequestBody CommonLogCreateReqVO createReqVO) {
        return success(commonLogService.createCommonLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新管控记录")
    @PreAuthorize("@ss.hasPermission('jl:common-log:update')")
    public CommonResult<Boolean> updateCommonLog(@Valid @RequestBody CommonLogUpdateReqVO updateReqVO) {
        commonLogService.updateCommonLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除管控记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:common-log:delete')")
    public CommonResult<Boolean> deleteCommonLog(@RequestParam("id") Long id) {
        commonLogService.deleteCommonLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得管控记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:common-log:query')")
    public CommonResult<CommonLogRespVO> getCommonLog(@RequestParam("id") Long id) {
            Optional<CommonLog> commonLog = commonLogService.getCommonLog(id);
        return success(commonLog.map(commonLogMapper::toDto).orElseThrow(() -> exception(COMMON_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得管控记录列表")
    @PreAuthorize("@ss.hasPermission('jl:common-log:query')")
    public CommonResult<PageResult<CommonLogRespVO>> getCommonLogPage(@Valid CommonLogPageReqVO pageVO, @Valid CommonLogPageOrder orderV0) {
        PageResult<CommonLog> pageResult = commonLogService.getCommonLogPage(pageVO, orderV0);
        return success(commonLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出管控记录 Excel")
    @PreAuthorize("@ss.hasPermission('jl:common-log:export')")
    @OperateLog(type = EXPORT)
    public void exportCommonLogExcel(@Valid CommonLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CommonLog> list = commonLogService.getCommonLogList(exportReqVO);
        // 导出 Excel
        List<CommonLogExcelVO> excelData = commonLogMapper.toExcelList(list);
        ExcelUtils.write(response, "管控记录.xls", "数据", CommonLogExcelVO.class, excelData,new CommonLogExcelCellWriterHandler());
    }

}

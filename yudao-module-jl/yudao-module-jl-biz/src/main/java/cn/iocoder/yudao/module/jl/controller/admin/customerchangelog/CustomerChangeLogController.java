package cn.iocoder.yudao.module.jl.controller.admin.customerchangelog;

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

import cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.customerchangelog.CustomerChangeLog;
import cn.iocoder.yudao.module.jl.mapper.customerchangelog.CustomerChangeLogMapper;
import cn.iocoder.yudao.module.jl.service.customerchangelog.CustomerChangeLogService;

@Tag(name = "管理后台 - 客户变更日志")
@RestController
@RequestMapping("/jl/customer-change-log")
@Validated
public class CustomerChangeLogController {

    @Resource
    private CustomerChangeLogService customerChangeLogService;

    @Resource
    private CustomerChangeLogMapper customerChangeLogMapper;

    @PostMapping("/create")
    @Operation(summary = "创建客户变更日志")
    @PreAuthorize("@ss.hasPermission('jl:customer-change-log:create')")
    public CommonResult<Long> createCustomerChangeLog(@Valid @RequestBody CustomerChangeLogCreateReqVO createReqVO) {
        return success(customerChangeLogService.createCustomerChangeLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户变更日志")
    @PreAuthorize("@ss.hasPermission('jl:customer-change-log:update')")
    public CommonResult<Boolean> updateCustomerChangeLog(@Valid @RequestBody CustomerChangeLogUpdateReqVO updateReqVO) {
        customerChangeLogService.updateCustomerChangeLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除客户变更日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:customer-change-log:delete')")
    public CommonResult<Boolean> deleteCustomerChangeLog(@RequestParam("id") Long id) {
        customerChangeLogService.deleteCustomerChangeLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得客户变更日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:customer-change-log:query')")
    public CommonResult<CustomerChangeLogRespVO> getCustomerChangeLog(@RequestParam("id") Long id) {
            Optional<CustomerChangeLog> customerChangeLog = customerChangeLogService.getCustomerChangeLog(id);
        return success(customerChangeLog.map(customerChangeLogMapper::toDto).orElseThrow(() -> exception(CUSTOMER_CHANGE_LOG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得客户变更日志列表")
    @PreAuthorize("@ss.hasPermission('jl:customer-change-log:query')")
    public CommonResult<PageResult<CustomerChangeLogRespVO>> getCustomerChangeLogPage(@Valid CustomerChangeLogPageReqVO pageVO, @Valid CustomerChangeLogPageOrder orderV0) {
        PageResult<CustomerChangeLog> pageResult = customerChangeLogService.getCustomerChangeLogPage(pageVO, orderV0);
        return success(customerChangeLogMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户变更日志 Excel")
    @PreAuthorize("@ss.hasPermission('jl:customer-change-log:export')")
    @OperateLog(type = EXPORT)
    public void exportCustomerChangeLogExcel(@Valid CustomerChangeLogExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CustomerChangeLog> list = customerChangeLogService.getCustomerChangeLogList(exportReqVO);
        // 导出 Excel
        List<CustomerChangeLogExcelVO> excelData = customerChangeLogMapper.toExcelList(list);
        ExcelUtils.write(response, "客户变更日志.xls", "数据", CustomerChangeLogExcelVO.class, excelData);
    }

}

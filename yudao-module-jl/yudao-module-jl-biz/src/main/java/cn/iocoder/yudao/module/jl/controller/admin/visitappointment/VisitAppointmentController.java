package cn.iocoder.yudao.module.jl.controller.admin.visitappointment;

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

import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitappointment.VisitAppointment;
import cn.iocoder.yudao.module.jl.mapper.visitappointment.VisitAppointmentMapper;
import cn.iocoder.yudao.module.jl.service.visitappointment.VisitAppointmentService;

@Tag(name = "管理后台 - 晶莱到访预约")
@RestController
@RequestMapping("/jl/visit-appointment")
@Validated
public class VisitAppointmentController {

    @Resource
    private VisitAppointmentService visitAppointmentService;

    @Resource
    private VisitAppointmentMapper visitAppointmentMapper;

    @PostMapping("/create")
    @Operation(summary = "创建晶莱到访预约")
    @PreAuthorize("@ss.hasPermission('jl:visit-appointment:create')")
    public CommonResult<Long> createVisitAppointment(@Valid @RequestBody VisitAppointmentCreateReqVO createReqVO) {
        return success(visitAppointmentService.createVisitAppointment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新晶莱到访预约")
    @PreAuthorize("@ss.hasPermission('jl:visit-appointment:update')")
    public CommonResult<Boolean> updateVisitAppointment(@Valid @RequestBody VisitAppointmentUpdateReqVO updateReqVO) {
        visitAppointmentService.updateVisitAppointment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除晶莱到访预约")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:visit-appointment:delete')")
    public CommonResult<Boolean> deleteVisitAppointment(@RequestParam("id") Long id) {
        visitAppointmentService.deleteVisitAppointment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得晶莱到访预约")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:visit-appointment:query')")
    public CommonResult<VisitAppointmentRespVO> getVisitAppointment(@RequestParam("id") Long id) {
            Optional<VisitAppointment> visitAppointment = visitAppointmentService.getVisitAppointment(id);
        return success(visitAppointment.map(visitAppointmentMapper::toDto).orElseThrow(() -> exception(VISIT_APPOINTMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得晶莱到访预约列表")
    @PreAuthorize("@ss.hasPermission('jl:visit-appointment:query')")
    public CommonResult<PageResult<VisitAppointmentRespVO>> getVisitAppointmentPage(@Valid VisitAppointmentPageReqVO pageVO, @Valid VisitAppointmentPageOrder orderV0) {
        PageResult<VisitAppointment> pageResult = visitAppointmentService.getVisitAppointmentPage(pageVO, orderV0);
        return success(visitAppointmentMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出晶莱到访预约 Excel")
    @PreAuthorize("@ss.hasPermission('jl:visit-appointment:export')")
    @OperateLog(type = EXPORT)
    public void exportVisitAppointmentExcel(@Valid VisitAppointmentExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<VisitAppointment> list = visitAppointmentService.getVisitAppointmentList(exportReqVO);
        // 导出 Excel
        List<VisitAppointmentExcelVO> excelData = visitAppointmentMapper.toExcelList(list);
        ExcelUtils.write(response, "晶莱到访预约.xls", "数据", VisitAppointmentExcelVO.class, excelData);
    }

}

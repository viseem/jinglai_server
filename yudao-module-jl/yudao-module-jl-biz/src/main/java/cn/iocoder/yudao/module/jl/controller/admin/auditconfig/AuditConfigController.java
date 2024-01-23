package cn.iocoder.yudao.module.jl.controller.admin.auditconfig;

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

import cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo.*;
import cn.iocoder.yudao.module.jl.entity.auditconfig.AuditConfig;
import cn.iocoder.yudao.module.jl.mapper.auditconfig.AuditConfigMapper;
import cn.iocoder.yudao.module.jl.service.auditconfig.AuditConfigService;

@Tag(name = "管理后台 - 审批配置表 ")
@RestController
@RequestMapping("/jl/audit-config")
@Validated
public class AuditConfigController {

    @Resource
    private AuditConfigService auditConfigService;

    @Resource
    private AuditConfigMapper auditConfigMapper;

    @PostMapping("/create")
    @Operation(summary = "创建审批配置表 ")
    @PreAuthorize("@ss.hasPermission('jl:audit-config:create')")
    public CommonResult<Long> createAuditConfig(@Valid @RequestBody AuditConfigCreateReqVO createReqVO) {
        return success(auditConfigService.createAuditConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新审批配置表 ")
    @PreAuthorize("@ss.hasPermission('jl:audit-config:update')")
    public CommonResult<Boolean> updateAuditConfig(@Valid @RequestBody AuditConfigUpdateReqVO updateReqVO) {
        auditConfigService.updateAuditConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除审批配置表 ")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:audit-config:delete')")
    public CommonResult<Boolean> deleteAuditConfig(@RequestParam("id") Long id) {
        auditConfigService.deleteAuditConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得审批配置表 ")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:audit-config:query')")
    public CommonResult<AuditConfigRespVO> getAuditConfig(@RequestParam("id") Long id) {
            Optional<AuditConfig> auditConfig = auditConfigService.getAuditConfig(id);
        return success(auditConfig.map(auditConfigMapper::toDto).orElseThrow(() -> exception(AUDIT_CONFIG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得审批配置表 列表")
    @PreAuthorize("@ss.hasPermission('jl:audit-config:query')")
    public CommonResult<PageResult<AuditConfigRespVO>> getAuditConfigPage(@Valid AuditConfigPageReqVO pageVO, @Valid AuditConfigPageOrder orderV0) {
        PageResult<AuditConfig> pageResult = auditConfigService.getAuditConfigPage(pageVO, orderV0);
        return success(auditConfigMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出审批配置表  Excel")
    @PreAuthorize("@ss.hasPermission('jl:audit-config:export')")
    @OperateLog(type = EXPORT)
    public void exportAuditConfigExcel(@Valid AuditConfigExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<AuditConfig> list = auditConfigService.getAuditConfigList(exportReqVO);
        // 导出 Excel
        List<AuditConfigExcelVO> excelData = auditConfigMapper.toExcelList(list);
        ExcelUtils.write(response, "审批配置表 .xls", "数据", AuditConfigExcelVO.class, excelData);
    }

}

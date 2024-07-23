package cn.iocoder.yudao.module.jl.controller.admin.projectsettlement;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsettlement.ProjectSettlement;
import cn.iocoder.yudao.module.jl.mapper.projectsettlement.ProjectSettlementMapper;
import cn.iocoder.yudao.module.jl.service.projectsettlement.ProjectSettlementService;

@Tag(name = "管理后台 - 项目结算")
@RestController
@RequestMapping("/jl/project-settlement")
@Validated
public class ProjectSettlementController {

    @Resource
    private ProjectSettlementService projectSettlementService;

    @Resource
    private ProjectSettlementMapper projectSettlementMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目结算")
    @PreAuthorize("@ss.hasPermission('jl:project-settlement:create')")
    public CommonResult<Long> createProjectSettlement(@Valid @RequestBody ProjectSettlementCreateReqVO createReqVO) {
        return success(projectSettlementService.createProjectSettlement(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目结算")
    @PreAuthorize("@ss.hasPermission('jl:project-settlement:update')")
    public CommonResult<Boolean> updateProjectSettlement(@Valid @RequestBody ProjectSettlementUpdateReqVO updateReqVO) {
        projectSettlementService.updateProjectSettlement(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目结算")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-settlement:delete')")
    public CommonResult<Boolean> deleteProjectSettlement(@RequestParam("id") Long id) {
        projectSettlementService.deleteProjectSettlement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目结算")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-settlement:query')")
    public CommonResult<ProjectSettlementRespVO> getProjectSettlement(@RequestParam("id") Long id) {
            Optional<ProjectSettlement> projectSettlement = projectSettlementService.getProjectSettlement(id);
        return success(projectSettlement.map(projectSettlementMapper::toDto).orElseThrow(() -> exception(PROJECT_SETTLEMENT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目结算列表")
    @PreAuthorize("@ss.hasPermission('jl:project-settlement:query')")
    public CommonResult<PageResult<ProjectSettlementRespVO>> getProjectSettlementPage(@Valid ProjectSettlementPageReqVO pageVO, @Valid ProjectSettlementPageOrder orderV0) {
        PageResult<ProjectSettlement> pageResult = projectSettlementService.getProjectSettlementPage(pageVO, orderV0);
        return success(projectSettlementMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目结算 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-settlement:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectSettlementExcel(@Valid ProjectSettlementExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectSettlement> list = projectSettlementService.getProjectSettlementList(exportReqVO);
        // 导出 Excel
        List<ProjectSettlementExcelVO> excelData = projectSettlementMapper.toExcelList(list);
        ExcelUtils.write(response, "项目结算.xls", "数据", ProjectSettlementExcelVO.class, excelData);
    }

}

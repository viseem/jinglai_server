package cn.iocoder.yudao.module.jl.controller.admin.template;

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

import cn.iocoder.yudao.module.jl.controller.admin.template.vo.*;
import cn.iocoder.yudao.module.jl.entity.template.TemplateProjectPlan;
import cn.iocoder.yudao.module.jl.mapper.template.TemplateProjectPlanMapper;
import cn.iocoder.yudao.module.jl.service.template.TemplateProjectPlanService;

@Tag(name = "管理后台 - 项目方案模板")
@RestController
@RequestMapping("/jl/template-project-plan")
@Validated
public class TemplateProjectPlanController {

    @Resource
    private TemplateProjectPlanService templateProjectPlanService;

    @Resource
    private TemplateProjectPlanMapper templateProjectPlanMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目方案模板")
    @PreAuthorize("@ss.hasPermission('jl:template-project-plan:create')")
    public CommonResult<Long> createTemplateProjectPlan(@Valid @RequestBody TemplateProjectPlanCreateReqVO createReqVO) {
        return success(templateProjectPlanService.createTemplateProjectPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目方案模板")
    @PreAuthorize("@ss.hasPermission('jl:template-project-plan:update')")
    public CommonResult<Boolean> updateTemplateProjectPlan(@Valid @RequestBody TemplateProjectPlanUpdateReqVO updateReqVO) {
        templateProjectPlanService.updateTemplateProjectPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目方案模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:template-project-plan:delete')")
    public CommonResult<Boolean> deleteTemplateProjectPlan(@RequestParam("id") Long id) {
        templateProjectPlanService.deleteTemplateProjectPlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目方案模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:template-project-plan:query')")
    public CommonResult<TemplateProjectPlanRespVO> getTemplateProjectPlan(@RequestParam("id") Long id) {
            Optional<TemplateProjectPlan> templateProjectPlan = templateProjectPlanService.getTemplateProjectPlan(id);
        return success(templateProjectPlan.map(templateProjectPlanMapper::toDto).orElseThrow(() -> exception(TEMPLATE_PROJECT_PLAN_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目方案模板列表")
    @PreAuthorize("@ss.hasPermission('jl:template-project-plan:query')")
    public CommonResult<PageResult<TemplateProjectPlanRespVO>> getTemplateProjectPlanPage(@Valid TemplateProjectPlanPageReqVO pageVO, @Valid TemplateProjectPlanPageOrder orderV0) {
        PageResult<TemplateProjectPlan> pageResult = templateProjectPlanService.getTemplateProjectPlanPage(pageVO, orderV0);
        return success(templateProjectPlanMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目方案模板 Excel")
    @PreAuthorize("@ss.hasPermission('jl:template-project-plan:export')")
    @OperateLog(type = EXPORT)
    public void exportTemplateProjectPlanExcel(@Valid TemplateProjectPlanExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<TemplateProjectPlan> list = templateProjectPlanService.getTemplateProjectPlanList(exportReqVO);
        // 导出 Excel
        List<TemplateProjectPlanExcelVO> excelData = templateProjectPlanMapper.toExcelList(list);
        ExcelUtils.write(response, "项目方案模板.xls", "数据", TemplateProjectPlanExcelVO.class, excelData);
    }

}

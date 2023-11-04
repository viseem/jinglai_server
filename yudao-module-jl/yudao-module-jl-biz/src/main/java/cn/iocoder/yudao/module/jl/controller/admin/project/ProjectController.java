package cn.iocoder.yudao.module.jl.controller.admin.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.repository.projectperson.ProjectPersonRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectScheduleService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectService;

@Tag(name = "管理后台 - 项目管理")
@RestController
@RequestMapping("/jl/project")
@Validated
public class ProjectController {
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectPersonRepository projectPersonRepository;

    @Resource
    private ProjectScheduleService projectScheduleService;

    @Resource
    private ProjectMapper projectMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目管理")
    @PreAuthorize("@ss.hasPermission('jl:project:create')")
    public CommonResult<Long> createProject(@Valid @RequestBody ProjectCreateReqVO createReqVO) {
        return success(projectService.createProject(createReqVO));
    }

    //发起出库申请 其实就是改个状态，还是单独写个接口吧
    @PutMapping("/outbound-apply")
    @Operation(summary = "项目出库申请")
    @PreAuthorize("@ss.hasPermission('jl:project:update')")
    public CommonResult<Boolean> projectOutboundApply(@Valid @RequestBody ProjectOutboundApplyReqVO updateReqVO) {
        projectService.projectOutboundApply(updateReqVO);
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目管理")
    @PreAuthorize("@ss.hasPermission('jl:project:update')")
    public CommonResult<Boolean> updateProject(@Valid @RequestBody ProjectUpdateReqVO updateReqVO) {
        projectService.updateProject(updateReqVO);
        return success(true);
    }

    @PutMapping("/current-schedule")
    @Operation(summary = "设置当前的主安排单")
    @PreAuthorize("@ss.hasPermission('jl:project:update')")
    public CommonResult<Boolean> setCurrentSchedule(@Valid @RequestBody ProjectSetCurrentScheduleReqVO updateReqVO) {
        projectService.setProjectCurrentSchedule(updateReqVO.getProjectId(), updateReqVO.getScheduleId());
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project:delete')")
    public CommonResult<Boolean> deleteProject(@RequestParam("id") Long id) {
        projectService.deleteProject(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<ProjectRespVO> getProject(@RequestParam("id") Long id) {
        Optional<Project> project = projectService.getProject(id);
        ProjectRespVO ret = project.map(projectMapper::toDto).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));

        // 计算各类成本
/*        Long currentScheduleId =ret.getCurrentScheduleId();
        ret.setSupplyCost(projectScheduleService.getSupplyCostByScheduleId(currentScheduleId));
        ret.setChargeItemCost(projectScheduleService.getChargeItemCostByScheduleId(currentScheduleId));
        ret.setOutsourceCost(projectScheduleService.getCategoryOutSourceCostByScheduleId(currentScheduleId));
        ret.setReimbursementCost(projectScheduleService.getReimburseCostByScheduleId(currentScheduleId));
        ret.setProcurementCost(projectScheduleService.getProcurementCostByScheduleId(currentScheduleId));*/

        //查询persons人员,通过ProjectPerson表查询，然后通过personId查询person表
        ret.setPersons(projectPersonRepository.findByProjectId(id));

        return success(ret);
    }

    @GetMapping("/cost-stats")
    @Operation(summary = "通过 ID 获得项目管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<ProjectCostStatsRespVO> getProjectCostStats(@RequestParam("id") Long id) {
        Project project = projectService.getProject(id).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));
        // 计算各类成本
        Long currentScheduleId =project.getCurrentScheduleId();
        ProjectCostStatsRespVO ret = new ProjectCostStatsRespVO();
        ret.setSupplyCost(projectScheduleService.getSupplyCostByScheduleId(currentScheduleId));
        ret.setChargeItemCost(projectScheduleService.getChargeItemCostByScheduleId(currentScheduleId));
        ret.setOutsourceCost(projectScheduleService.getCategoryOutSourceCostByScheduleId(currentScheduleId));
        ret.setReimbursementCost(projectScheduleService.getReimburseCostByScheduleId(currentScheduleId));
        ret.setProcurementCost(projectScheduleService.getProcurementCostByScheduleId(currentScheduleId));

        return success(ret);
    }

    @GetMapping("/count-stats")
    @Operation(summary = "(分页)获得项目管理列表")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<ProjectStatsRespVO> getProjectStats(@Valid ProjectPageReqVO pageVO) {
        ProjectStatsRespVO projectStatsRespVO = projectService.getProjectStats(pageVO);
        return success(projectStatsRespVO);
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目管理列表")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<PageResult<ProjectSimple>> getProjectPage(@Valid ProjectPageReqVO pageVO, @Valid ProjectPageOrder orderV0) {
        PageResult<ProjectSimple> pageResult = projectService.getProjectPage(pageVO, orderV0);
        return success(pageResult);
    }

    @GetMapping("/supply-and-charge")
    @Operation(summary = "项目物资")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<ProjectSupplyAndChargeRespVO> getProjectPage(@Valid ProjectSupplyAndChargeReqVO pageVO) {
        ProjectSupplyAndChargeRespVO projectSupplyAndCharge = projectService.getProjectSupplyAndCharge(pageVO);
        return success(projectSupplyAndCharge);
    }

    @GetMapping("/simple-page")
    @Operation(summary = "(分页)获得项目管理列表")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<PageResult<ProjectRespVO>> getProjectSimplePage(@Valid ProjectPageReqVO pageVO, @Valid ProjectPageOrder orderV0) {
        PageResult<ProjectSimple> pageResult = projectService.getProjectSimplePage(pageVO, orderV0);
        return success(projectMapper.toSimplePage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目管理 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectExcel(@Valid ProjectExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Project> list = projectService.getProjectList(exportReqVO);
        // 导出 Excel
        List<ProjectExcelVO> excelData = projectMapper.toExcelList(list);
        ExcelUtils.write(response, "项目管理.xls", "数据", ProjectExcelVO.class, excelData);
    }

}

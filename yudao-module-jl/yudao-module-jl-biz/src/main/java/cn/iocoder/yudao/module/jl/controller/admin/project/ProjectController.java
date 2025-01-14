package cn.iocoder.yudao.module.jl.controller.admin.project;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo2.ProjectOutLogStep1Json;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo2.ProjectOutLogStep3Json;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.repository.projectperson.ProjectPersonRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectScheduleService;
import cn.iocoder.yudao.module.system.api.dict.DictDataApiImpl;
import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

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

    @Resource
    private DictDataApiImpl dictDataApi;

    @PostMapping("/create")
    @Operation(summary = "创建项目管理")
    @PreAuthorize("@ss.hasPermission('jl:project:create')")
    public CommonResult<Long> createProject(@Valid @RequestBody ProjectCreateReqVO createReqVO) {
        return success(projectService.createProject(createReqVO));
    }

    @PostMapping("/bind")
    @Operation(summary = "创建项目管理")
    @PreAuthorize("@ss.hasPermission('jl:project:create')")
    public CommonResult<Boolean> bindProject(@Valid @RequestBody ProjectBindReqVO createReqVO) {
        projectService.bindProject(createReqVO);
        return success(true);
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

    @PutMapping("/update-tag")
    @Operation(summary = "更新项目的标签")
    @PreAuthorize("@ss.hasPermission('jl:project:update')")
    public CommonResult<Boolean> updateProjectTag(@Valid @RequestBody ProjectUpdateTagReqVO updateReqVO) {
        projectService.updateProjectTag(updateReqVO);
        return success(true);
    }

    @PutMapping("/current-schedule")
    @Operation(summary = "设置当前的主安排单")
    @PreAuthorize("@ss.hasPermission('jl:project:update')")
    public CommonResult<Boolean> setCurrentSchedule(@Valid @RequestBody ProjectSetCurrentScheduleReqVO updateReqVO) {
        projectService.setProjectCurrentSchedule(updateReqVO.getProjectId(), updateReqVO.getScheduleId());
        return success(true);
    }

    @PostMapping("/to-seas-or-receive")
    @Operation(summary = "项目转入公海池 或者 领取")
    @PreAuthorize("@ss.hasPermission('jl:saleslead:update')")
    public CommonResult<Boolean> projectToSeasOrReceive(@Valid @RequestBody ProjectSeasVO updateReqVO) {
        projectService.projectToSeasOrReceive(updateReqVO);
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
//        ret.setPersons(projectPersonRepository.findByProjectId(id));

        return success(ret);
    }

    @GetMapping("/cost-stats")
    @Operation(summary = "通过 ID 获得项目管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<ProjectCostStatsRespVO> getProjectCostStats(@RequestParam("id") Long id) {
        Project project = projectService.getProject(id).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));

        // 计算各类成本
        Long quotationId =project.getCurrentQuotationId();
        ProjectCostStatsRespVO ret = new ProjectCostStatsRespVO();

        //计算合同应收和已收 TODO 可以合并成一个方法
        ret.setContractAmount(projectScheduleService.getContractAmountByProjectId(id));
        ret.setContractReceivedAmount(projectScheduleService.getContractReceivedAmountByProjectId(id));

        ret.setSupplyCost(projectScheduleService.getSupplyQuotationByQuotationId(quotationId));
        ret.setChargeItemCost(projectScheduleService.getChargeItemQuotationByQuotationId(quotationId));
        ret.setInvoiceAmount(projectScheduleService.getInvoiceAmountByProjectId(id));
        ret.setOutsourceCost(projectScheduleService.getCategoryOutSourceCostByProjectId(id));
        ret.setReimbursementCost(projectScheduleService.getReimburseCostByProjectId(id));
        ret.setProcurementCost(projectScheduleService.getProcurementCostByProjectId(id));
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
    public CommonResult<PageResult<Project>> getProjectPage(@Valid ProjectPageReqVO pageVO, @Valid ProjectPageOrder orderV0) {
        PageResult<Project> pageResult = projectService.getProjectPage(pageVO, orderV0);
        return success(pageResult);
    }

    @GetMapping("/page-only")
    @Operation(summary = "(分页)获得项目管理列表")
    @PreAuthorize("@ss.hasPermission('jl:project:query')")
    public CommonResult<PageResult<ProjectOnly>> getProjectPageOnly(@Valid ProjectPageReqVO pageVO, @Valid ProjectPageOrder orderV0) {
        PageResult<ProjectOnly> pageResult = projectService.getProjectPageOnly(pageVO, orderV0);
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
    public void exportProjectExcel(@Valid ProjectPageReqVO exportReqVO, HttpServletResponse response) throws IOException {
        PageResult<Project> projectList = projectService.getProjectList(exportReqVO);
        // 导出 Excel
        List<ProjectExcelVO> excelData = new ArrayList<>();
        if(projectList.getList()!=null){
            for (Project project : projectList.getList()) {
                ProjectExcelVO item = new ProjectExcelVO();
                item.setName(project.getName());
                item.setCustomerName(project.getCustomer()!=null?project.getCustomer().getName():"");
                item.setStartTime(project.getStartDate()!=null?project.getStartDate().format(DateTimeFormatter.ofPattern("yyyy/M/d")):"");
                item.setEndTime(project.getEndDate()!=null?project.getEndDate().format(DateTimeFormatter.ofPattern("yyyy/M/d")):"");
                // Date格式化project.getOutProcessInstance().getEndTime()
                item.setOutTime(project.getOutProcessInstance()!=null?new java.text.SimpleDateFormat("yyyy/M/d").format(project.getOutProcessInstance().getEndTime()):"");

                item.setSalesName(project.getSales()!=null?project.getSales().getNickname():"");
                item.setPreManagerName(project.getPreManager()!=null?project.getPreManager().getNickname():"");
                item.setManagerName(project.getManager()!=null?project.getManager().getNickname():"");
                if(project.getType()!=null){
                    List<DictDataRespDTO> types = dictDataApi.getDictDataByType(DictTypeConstants.PROJECT_TYPE);
                    for (DictDataRespDTO type : types) {
                        if (type.getValue().equals(project.getType())) {
                            item.setTypeName(type.getLabel());
                        }
                    }
                }

                // 把project.outLog.step1Json中的json数据，覆盖到item包含的值
                if(project.getOutLog()!=null){
                    try{
                        BeanUtil.copyProperties(JSONUtil.toBean(project.getOutLog().getStep1Json(), ProjectOutLogStep1Json.class), item);
                        ProjectOutLogStep3Json json3 = JSONUtil.toBean(project.getOutLog().getStep3Json(), ProjectOutLogStep3Json.class);
                        //如果json3不为空，计算customerScores的value和，value是字符串数字,先转为BigDecimal再相加
                        if (json3 != null&&json3.getCustomerScores()!=null) {
                            BigDecimal customerScore = json3.getCustomerScores().stream()
                                    .map(row1 -> new BigDecimal(row1.getValue()))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            item.setCustomerScore(customerScore.toString());
                        }
                    }catch (Exception ignored){}
                }


                // 计算project的contractList的price的和，contract需要满足type=1
                if(project.getContractList()!=null){
                    List<ProjectConstractOnly> contractList = project.getContractList().stream().filter(contract -> contract.getStatus().equals(ProjectContractStatusEnums.SIGNED.getStatus())).collect(Collectors.toList());
                    if(!contractList.isEmpty()){
                        try{
                            item.setContractSn(contractList.get(0).getSn());
                            item.setSignedTime(contractList.get(0).getSignedTime()!=null?contractList.get(0).getSignedTime().format(DateTimeFormatter.ofPattern("yyyy/M/d")):"");
                            BigDecimal totalPrice = contractList.stream().map(ProjectConstractOnly::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                            item.setContractAmount(totalPrice.toString());
                        }catch (Exception ignored){}
                   }
                }
                excelData.add(item);
            }
        }
        ExcelUtils.write(response, "项目管理.xls", "数据", ProjectExcelVO.class, excelData);
    }



}

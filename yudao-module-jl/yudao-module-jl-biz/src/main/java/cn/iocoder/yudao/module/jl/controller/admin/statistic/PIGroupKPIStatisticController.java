package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.PIGroupKPIStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.PIGroupRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo.SubjectGroupMemberRespVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.enums.SubjectGroupMemberRoleEnums;
import cn.iocoder.yudao.module.jl.mapper.subjectgroup.SubjectGroupMapper;
import cn.iocoder.yudao.module.jl.mapper.subjectgroupmember.SubjectGroupMemberMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSimpleRepository;
import cn.iocoder.yudao.module.jl.repository.subjectgroup.SubjectGroupRepository;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - PI组 KPI 数据统计")
@RestController
@RequestMapping("/statistics/pi")
@Validated
public class PIGroupKPIStatisticController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 合同 Repository
    @Resource
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private SubjectGroupMapper subjectGroupMapper;

    @Resource
    private SubjectGroupMemberMapper subjectGroupMemberMapper;

    @Resource
    private SubjectGroupMemberService subjectGroupMemberService;

    @Resource
    private SubjectGroupRepository subjectGroupRepository;

    @Resource
    private ProjectSimpleRepository projectSimpleRepository;

    @GetMapping("/kpi")
    @Operation(summary = "PI组 KPI 统计")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<PIGroupKPIStatisticResp> getFinancialStatistic (
            @RequestParam(name = "subjectGroupId", required = false) Long subjectGroupId
    ) {

        PIGroupKPIStatisticResp resp = new PIGroupKPIStatisticResp();

        // 查询PI组的KPI
        SubjectGroup piGroup = subjectGroupRepository.findById(subjectGroupId).orElseThrow(() -> new IllegalArgumentException("PI组不存在"));
        BigDecimal orderFundKpi = piGroup.getKpiOrderFund();  // 订单 KPI
        BigDecimal returnFundKpi = piGroup.getKpiReturnFund(); // 回款 KPI
        PIGroupRespVO piDto = subjectGroupMapper.toPIDto(piGroup);
        piDto.setKpiOrderFundOf80(orderFundKpi.multiply(new BigDecimal("0.8")));
        piDto.setKpiOrderFundOf120(orderFundKpi.multiply(new BigDecimal("1.2")));
        piDto.setKpiReturnFundOf80(returnFundKpi.multiply(new BigDecimal("0.8")));
        piDto.setKpiReturnFundOf120(returnFundKpi.multiply(new BigDecimal("1.2")));
        resp.setBasePIGroup(piDto);

        // 获取 PI 组成员
        List<SubjectGroupMember> members = subjectGroupMemberService.findMembersByGroupId(subjectGroupId);
        List<SubjectGroupMemberRespVO> dtoList = subjectGroupMemberMapper.toDtoList(members);
        dtoList.forEach(item->{
                // 销售
                if(Objects.equals(item.getRole(), SubjectGroupMemberRoleEnums.SALE.getStatus())){
                    List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.findByCreatorInAndCreateTimeBetweenAndStatus(new Long[]{item.getUserId()}, StatisticUtils.getStartTimeByTimeRange("month"), LocalDateTime.now(), ProjectContractStatusEnums.SIGNED.getStatus());
                    for (ProjectConstractOnly contract : contractList) {
                        if(contract.getPrice() != null) {
                            item.setMonthOrderFund(item.getMonthOrderFund().add(contract.getPrice()));
                        }
                        if (contract.getReceivedPrice() != null) {
                            item.setMonthReturnFund(item.getMonthReturnFund().add(contract.getReceivedPrice()));
                        }
                    }
                }
                //项目
                if(Objects.equals(item.getRole(), SubjectGroupMemberRoleEnums.PROJECT.getStatus())){
                    // 手头未出库项目数
                    item.setNotOutProjectNum(projectSimpleRepository.countByStageNotAndManagerInAndCodeNotNull(ProjectStageEnums.OUTED.getStatus(), new Long[]{item.getUserId()}));
//                    item.setMonthNotOutProjectCount(subjectGroupMemberService.countNotOutProjectByUserId(item.getUserId()));
                    // 2周内到期的项目数
                    item.setTwoWeekExpireProjectNum(projectSimpleRepository.countByEndDateBetweenAndManagerInAndCodeNotNull(LocalDate.now(), LocalDate.now().plusDays(14), new Long[]{item.getUserId()}));
//                    item.setMonthExpireProjectCount(subjectGroupMemberService.countExpireProjectByUserId(item.getUserId()));
                }


        });
        resp.setMembers(dtoList);

        // 查询 PI 组成员的 KPI完成情况，
        // 销售：订单完成情况，回款完成情况
        // 项目经理：手头未出库项目数，2周内到期的项目数

        return success(resp);
    }
}

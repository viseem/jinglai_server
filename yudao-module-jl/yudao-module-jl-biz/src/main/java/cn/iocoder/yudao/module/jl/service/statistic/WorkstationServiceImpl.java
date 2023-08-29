package cn.iocoder.yudao.module.jl.service.statistic;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationProjectCountStatsResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationSaleCountStatsResp;
import cn.iocoder.yudao.module.jl.enums.ProjectFundEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.enums.SalesLeadStatusEnums;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectFundRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Service
@Validated
public class WorkstationServiceImpl implements WorkstationService {

    @Resource
    private SalesleadRepository salesleadRepository;

    @Resource
    private ProjectFundRepository projectFundRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Override
    public WorkstationSaleCountStatsResp getSaleCountStats() {
        WorkstationSaleCountStatsResp resp = new WorkstationSaleCountStatsResp();

        // 自己的未转化为项目的销售线索数量
        Integer notToProjectCount = salesleadRepository.countByCreatorAndStatusNot(getLoginUserId(), Integer.valueOf(SalesLeadStatusEnums.NotToProject.getStatus()));
        resp.setSalesLeadNotToProjectCount(notToProjectCount);
        // 自己的未收齐的款项数量
        Integer notPayCompleteCount = projectFundRepository.countByCreatorAndStatusNot(getLoginUserId(), ProjectFundEnums.ALL_PAY.getStatus());
        resp.setProjectFundNotPayCompleteCount(notPayCompleteCount);
        // 自己的未完成的项目数量
        Integer projectNotCompleteCount = projectRepository.countByCreatorAndStageNot(getLoginUserId(), ProjectStageEnums.OUTED.getStatus());
        resp.setProjectNotCompleteCount(projectNotCompleteCount);
        return resp;
    }

    @Override
    public WorkstationProjectCountStatsResp getProjectCountStats(){
        WorkstationProjectCountStatsResp resp = new WorkstationProjectCountStatsResp();

        //自己的未完成的项目数量
        Integer projectNotCompleteCount = projectRepository.countByManagerIdAndStageNot(getLoginUserId(), ProjectStageEnums.OUTED.getStatus());
        resp.setNotCompleteProjectCount(projectNotCompleteCount);
        return resp;
    }
}
package cn.iocoder.yudao.module.jl.service.statistic;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationSaleCountStatsResp;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class WorkstationServiceImpl implements WorkstationService {

/*    @Resource
    private SalesleadRepository salesleadRepository;

    @Resource
    private ProjectFundRepository projectFundRepository;

    @Resource
    private ProjectRepository projectRepository;*/

    @Override
    public WorkstationSaleCountStatsResp getSaleCountStats() {
        WorkstationSaleCountStatsResp resp = new WorkstationSaleCountStatsResp();

/*        // 自己的未转化为项目的销售线索数量
        Integer notToProjectCount = salesleadRepository.countByCreatorAndStatusNot(getLoginUserId(), Integer.valueOf(SalesLeadStatusEnums.NotToProject.getStatus()));
        resp.setSalesLeadNotToProjectCount(notToProjectCount);
        // 自己的未收齐的款项数量
        Integer notPayCompleteCount = projectFundRepository.countByCreatorAndStatusNot(getLoginUserId(), ProjectFundEnums.ALL_PAY.getStatus());
        resp.setProjectFundNotPayCompleteCount(notPayCompleteCount);
        // 自己的进行中的项目数量
        Integer projectDoingCount = projectRepository.countByCreatorAndStatus(getLoginUserId(), ProjectStageEnums.DOING.getStatus());
        resp.setProjectDoingCount(projectDoingCount);*/
        return resp;
    }
}
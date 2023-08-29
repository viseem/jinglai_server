package cn.iocoder.yudao.module.jl.service.statistic;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationExpCountStatsResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationFinanceCountStatsResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationProjectCountStatsResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.WorkstationSaleCountStatsResp;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
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

    @Resource
    private ProjectFeedbackRepository projectFeedbackRepository;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProcurementRepository procurementRepository;

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

        //所有的未处理的反馈数量
        Integer notProcessFeedbackCount = projectFeedbackRepository.countByStatusNot(ProjectFeedbackEnums.PROCESSED.getStatus());
        resp.setNotProcessFeedbackCount(notProcessFeedbackCount);

        return resp;
    }

    @Override
    public WorkstationExpCountStatsResp getExpCountStats(){
        WorkstationExpCountStatsResp resp = new WorkstationExpCountStatsResp();

        //自己的未完成的任务数量
        Integer notCompleteTaskCount = projectCategoryRepository.countByOperatorIdAndStageNot(getLoginUserId(), ProjectCategoryStatusEnums.COMPLETE.getStatus());
        resp.setNotCompleteTaskCount(notCompleteTaskCount);

        return resp;
    }

    @Override
    public WorkstationFinanceCountStatsResp getFinanceCountStats(){
        WorkstationFinanceCountStatsResp resp = new WorkstationFinanceCountStatsResp();

        // 全部的未收齐的款项数量
        Integer notPayCompleteCount = projectFundRepository.countByNotReceivedComplete();
        resp.setProjectFundNotPayCompleteCount(notPayCompleteCount);

        //全部的 未打款采购单数量
        Integer procurementNotPayCount = procurementRepository.countByStatus(ProcurementStatusEnums.WAITING_FINANCE_CONFIRM.getStatus());
        resp.setProcurementNotPayCount(procurementNotPayCount);

        return resp;
    }
}
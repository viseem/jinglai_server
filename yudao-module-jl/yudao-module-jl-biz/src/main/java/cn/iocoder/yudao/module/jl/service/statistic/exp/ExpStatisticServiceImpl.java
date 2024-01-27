package cn.iocoder.yudao.module.jl.service.statistic.exp;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp.ExpStatisticExpResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp.ExpStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.service.statistic.project.ProjectStatisticService;
import org.hibernate.annotations.Source;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 专题小组 Service 实现类
 *
 */
@Service
@Validated
public class ExpStatisticServiceImpl implements ExpStatisticService {

    @Source
    private ProjectCategoryRepository projectCategoryRepository;

    @Override
    public ExpStatisticExpResp countExp(ExpStatisticReqVO reqVO){

        Integer waitCount = projectCategoryRepository.countByCreateTimeBetweenAndOperatorIdInAndStage(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), ProjectCategoryStatusEnums.WAIT_DO.getStatus());
        Integer doingCount = projectCategoryRepository.countByCreateTimeBetweenAndOperatorIdInAndStageIn(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), ProjectCategoryStatusEnums.getDoingStages());
        Integer completeCount = projectCategoryRepository.countByCreateTimeBetweenAndOperatorIdInAndStage(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), ProjectCategoryStatusEnums.COMPLETE.getStatus());
        Integer pauseCount = projectCategoryRepository.countByCreateTimeBetweenAndOperatorIdInAndStage(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), ProjectCategoryStatusEnums.PAUSE.getStatus());

        ExpStatisticExpResp expStatisticExpResp = new ExpStatisticExpResp();
        expStatisticExpResp.setWaitCount(waitCount);
        expStatisticExpResp.setDoingCount(doingCount);
        expStatisticExpResp.setCompleteCount(completeCount);
        expStatisticExpResp.setPauseCount(pauseCount);

        return null;
    }
}
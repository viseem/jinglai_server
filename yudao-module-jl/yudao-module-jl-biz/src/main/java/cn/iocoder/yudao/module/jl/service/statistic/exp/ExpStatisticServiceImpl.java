package cn.iocoder.yudao.module.jl.service.statistic.exp;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp.ExpStatisticExpResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp.ExpStatisticReqVO;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 专题小组 Service 实现类
 *
 */
@Service
@Validated
public class ExpStatisticServiceImpl implements ExpStatisticService {

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private SubjectGroupMemberServiceImpl subjectGroupMemberService;

    @Override
    public ExpStatisticExpResp countExp(ExpStatisticReqVO reqVO){

        if(reqVO.getSubjectGroupId()!=null){
            //把返回的List中的id取出来
            reqVO.setUserIds(subjectGroupMemberService.findMembersUserIdsByGroupId(reqVO.getSubjectGroupId()));
        }
/*
        if(reqVO.getTimeRange()!=null){
            reqVO.setStartTime(StatisticUtils.getStartTimeByTimeRange(reqVO.getTimeRange()));
        }
*/

        Integer waitCount = projectCategoryRepository.countByOperatorIdInAndStageIn( reqVO.getUserIds(), new String[]{ProjectCategoryStatusEnums.WAIT_DO.getStatus()});
        Integer doingCount = projectCategoryRepository.countByOperatorIdInAndStageIn( reqVO.getUserIds(), ProjectCategoryStatusEnums.getDoingStages());
        Integer completeCount = projectCategoryRepository.countByOperatorIdInAndStageIn( reqVO.getUserIds(), new String[]{ProjectCategoryStatusEnums.DONE.getStatus()});
        Integer outCount = projectCategoryRepository.countByOperatorIdInAndStageIn( reqVO.getUserIds(), new String[]{ProjectCategoryStatusEnums.COMPLETE.getStatus()});
        Integer pauseCount = projectCategoryRepository.countByOperatorIdInAndStageIn( reqVO.getUserIds(), new String[]{ProjectCategoryStatusEnums.PAUSE.getStatus()});
        Integer delayCount = projectCategoryRepository.countByDeadlineLessThanAndOperatorIdInAndStageNot(LocalDateTime.now(), reqVO.getUserIds(), ProjectCategoryStatusEnums.COMPLETE.getStatus());
        Integer twoWeekExpireCount = projectCategoryRepository.countByDeadlineBetweenAndOperatorIdInAndStageNot(LocalDateTime.now(), LocalDateTime.now().plusDays(14), reqVO.getUserIds(), ProjectCategoryStatusEnums.COMPLETE.getStatus());
        ExpStatisticExpResp expStatisticExpResp = new ExpStatisticExpResp();
        expStatisticExpResp.setWaitCount(waitCount);
        expStatisticExpResp.setDoingCount(doingCount);
        expStatisticExpResp.setCompleteCount(completeCount);
        expStatisticExpResp.setOutCount(outCount);
        expStatisticExpResp.setPauseCount(pauseCount);
        expStatisticExpResp.setDelayCount(delayCount);
        expStatisticExpResp.setExpireCount(twoWeekExpireCount);
        return expStatisticExpResp;
    }
}
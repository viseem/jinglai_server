package cn.iocoder.yudao.module.jl.service.statistic.project;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectTagResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectStatusTagEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 专题小组 Service 实现类
 *
 */
@Service
@Validated
public class ProjectStatisticServiceImpl implements ProjectStatisticService {


    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private SubjectGroupMemberServiceImpl subjectGroupMemberService;

    @Override
    public ProjectStatisticProjectResp countProject(ProjectStatisticReqVO reqVO){

        if(reqVO.getSubjectGroupId()!=null){
            reqVO.setUserIds(subjectGroupMemberService.findMembersUserIdsByGroupId(reqVO.getSubjectGroupId()));
        }

        List<ProjectOnly> projectList = projectOnlyRepository.findByInManagerIdAndCodeNotNull(reqVO.getUserIds());

        ProjectStatisticProjectResp resp = new ProjectStatisticProjectResp();
        //实例化idsList
        List<Long> idList = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.PRE.getStatus()))
                .map(ProjectOnly::getId)  // 假设项目类中有一个getId方法来获取项目ID
                .collect(Collectors.toList());
        resp.setWaitCount(idList.size());

        List<Long> doingApprovalCount = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.DOING_PREVIEW.getStatus()))
                .map(ProjectOnly::getId)  // 假设项目类中有一个getId方法来获取项目ID
                .collect(Collectors.toList());
        resp.setDoingApprovalCount(doingApprovalCount.size());

        List<Long> doingIdList = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.DOING.getStatus()))
                .map(ProjectOnly::getId)
                .collect(Collectors.toList());
        resp.setDoingCount(doingIdList.size());

        List<Long> expArrangeIdList = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.EXP_ARRANGE.getStatus()))
                .map(ProjectOnly::getId)
                .collect(Collectors.toList());
        resp.setExpArrangeCount(expArrangeIdList.size());

        List<Long> settlementIdList = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.SETTLEMENT.getStatus()))
                .map(ProjectOnly::getId)
                .collect(Collectors.toList());
        resp.setSettlementCount(settlementIdList.size());

        List<Long> outApprovalIdList = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.OUTING.getStatus()))
                .map(ProjectOnly::getId)
                .collect(Collectors.toList());
        resp.setOutApprovalCount(outApprovalIdList.size());

        List<Long> delayIdList = projectList.stream()
                .filter(project -> project.getEndDate() != null &&
                        project.getEndDate().isBefore(LocalDate.now()) &&
                        !ProjectStageEnums.OUTED.getStatus().equals(project.getStage()))
                .map(ProjectOnly::getId)
                .collect(Collectors.toList());
        resp.setDelayCount(delayIdList.size());

        List<Long> expireIdList = projectList.stream()
                .filter(project -> project.getEndDate() != null &&
                        project.getEndDate().isAfter(LocalDate.now()) &&
                        project.getEndDate().isBefore(LocalDate.now().plusDays(14)) &&
                        !ProjectStageEnums.OUTED.getStatus().equals(project.getStage()))
                .map(ProjectOnly::getId)
                .collect(Collectors.toList());
        resp.setExpireCount(expireIdList.size());

        return resp;
    }

    @Override
    public ProjectStatisticProjectTagResp countProjectTag(ProjectStatisticReqVO reqVO){

        if(reqVO.getSubjectGroupId()!=null){
            reqVO.setUserIds(subjectGroupMemberService.findMembersUserIdsByGroupId(reqVO.getSubjectGroupId()));
        }

        List<ProjectOnly> projectList = projectOnlyRepository.findByInManagerIdAndCodeNotNull(reqVO.getUserIds());
        ProjectStatisticProjectTagResp resp = new ProjectStatisticProjectTagResp();

        long firstFundNotCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.FIRST_FUND_NOT.getStatus()))
                .count();
        resp.setFirstFundNotCount(firstFundNotCount);

        long firstFundDoneCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.FIRST_FUND_DONE.getStatus()))
                .count();
        resp.setFirstFundDoneCount(firstFundDoneCount);

        long doingCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.DOING.getStatus()))
                .count();
        resp.setDoingCount(doingCount);

        long pauseCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.PAUSE.getStatus()))
                .count();
        resp.setPauseCount(pauseCount);

        long stopCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.STOP.getStatus()))
                .count();
        resp.setStopCount(stopCount);

        long backCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.BACK.getStatus()))
                .count();
        resp.setBackCount(backCount);

        long outEarlyCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.OUT_EARLY.getStatus()))
                .count();
        resp.setOutEarlyCount(outEarlyCount);

        long doneWaitOutCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.DONE_WAIT_OUT.getStatus()))
                .count();
        resp.setDoneWaitOutCount(doneWaitOutCount);

        long doneWaitLastFundCount = projectList.stream()
                .filter(project -> containsStr(project.getTagIds(), ProjectStatusTagEnums.DONE_WAIT_LAST_FUND.getStatus()))
                .count();
        resp.setDoneWaitLastFundCount(doneWaitLastFundCount);

        return resp;

    }

    public static boolean containsStr(String numbersString, String targetNumber) {
        // 将逗号分隔的数字字符串分割成字符串数组
        String[] numbersArray = numbersString.split(",");

        // 遍历数组，检查是否包含目标数字
        for (String number : numbersArray) {
            // 将字符串转换为整数
            String currentNumber = number.trim();

            // 判断是否包含目标数字
            if (currentNumber.equals(targetNumber)) {
                return true;
            }
        }

        // 如果循环结束仍然没有找到目标数字，则返回false
        return false;
    }
}
package cn.iocoder.yudao.module.jl.service.statistic.project;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

        List<ProjectOnly> projectList = projectOnlyRepository.findByInManagerId(reqVO.getUserIds());

        ProjectStatisticProjectResp resp = new ProjectStatisticProjectResp();
        resp.setProjectList(projectList);
        long waitCount = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.PRE.getStatus()))
                .count();
        resp.setWaitCount(waitCount);

        long doingCount = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.DOING.getStatus()))
                .count();
        resp.setDoingCount(doingCount);

        long expArrangeCount = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.EXP_ARRANGE.getStatus()))
                .count();
        resp.setExpArrangeCount(expArrangeCount);

        long settlementCount = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.SETTLEMENT.getStatus()))
                .count();
        resp.setSettlementCount(settlementCount);

        long outApprovalCount = projectList.stream()
                .filter(project -> Objects.equals(project.getStage(), ProjectStageEnums.OUTING.getStatus()))
                .count();
        resp.setOutApprovalCount(outApprovalCount);

        long delayCount = projectList.stream()
                .filter(project -> project.getEndDate() != null &&
                        project.getEndDate().isBefore(LocalDate.now()) &&
                        !ProjectStageEnums.OUTED.getStatus().equals(project.getStage()))
                .count();
        resp.setDelayCount(delayCount);

        long expireCount = projectList.stream()
                .filter(project -> project.getEndDate() != null &&
                        project.getEndDate().isAfter(LocalDate.now()) &&
                        project.getEndDate().isBefore(LocalDate.now().plusDays(14)) &&
                        !ProjectStageEnums.OUTED.getStatus().equals(project.getStage()))
                .count();
        resp.setExpireCount(expireCount);

        return resp;
    }
}
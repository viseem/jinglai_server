package cn.iocoder.yudao.module.jl.service.statistic.project;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 专题小组 Service 实现类
 *
 */
@Service
@Validated
public class ProjectStatisticServiceImpl implements ProjectStatisticService {

    @Override
    public ProjectStatisticProjectResp countProject(ProjectStatisticReqVO reqVO){
        return null;
    }
}
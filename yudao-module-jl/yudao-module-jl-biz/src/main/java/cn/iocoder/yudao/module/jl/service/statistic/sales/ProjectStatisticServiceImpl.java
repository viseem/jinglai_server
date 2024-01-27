package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticFollowupResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticSalesleadResp;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.enums.SalesLeadStatusEnums;
import cn.iocoder.yudao.module.jl.repository.crm.FollowupRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

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
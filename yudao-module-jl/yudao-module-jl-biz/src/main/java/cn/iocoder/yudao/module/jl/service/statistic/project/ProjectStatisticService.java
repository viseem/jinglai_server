package cn.iocoder.yudao.module.jl.service.statistic.project;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticReqVO;

/**
 * 专题小组 Service 接口
 *
 */
public interface ProjectStatisticService {


    ProjectStatisticProjectResp countProject(ProjectStatisticReqVO reqVO);

}

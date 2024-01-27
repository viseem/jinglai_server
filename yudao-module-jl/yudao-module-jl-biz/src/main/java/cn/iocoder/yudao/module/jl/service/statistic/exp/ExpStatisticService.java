package cn.iocoder.yudao.module.jl.service.statistic.exp;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp.ExpStatisticExpResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp.ExpStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;

/**
 * 专题小组 Service 接口
 *
 */
public interface ExpStatisticService {


    ExpStatisticExpResp countExp(ExpStatisticReqVO reqVO);

}

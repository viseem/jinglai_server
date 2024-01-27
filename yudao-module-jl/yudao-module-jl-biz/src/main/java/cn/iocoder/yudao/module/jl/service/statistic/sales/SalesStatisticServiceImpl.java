package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticFollowupResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticReqVO;
import cn.iocoder.yudao.module.jl.repository.crm.FollowupRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 专题小组 Service 实现类
 *
 */
@Service
@Validated
public class SalesStatisticServiceImpl implements SalesStatisticService {

    @Resource
    private FollowupRepository followupRepository;

    @Override
    // 统计跟进数量
    public SalesStatisticFollowupResp countFollowup(SalesStatisticReqVO reqVO) {
        Integer followupCount = followupRepository.countByCreateTimeBetweenAndCreatorIn(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds());
        SalesStatisticFollowupResp resp = new SalesStatisticFollowupResp();
        resp.setFollowupCount(followupCount);
        return resp;
    }

}
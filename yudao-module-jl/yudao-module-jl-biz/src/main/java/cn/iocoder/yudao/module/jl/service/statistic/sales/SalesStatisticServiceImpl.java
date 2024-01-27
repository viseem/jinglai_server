package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticFollowupResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticSalesleadResp;
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
public class SalesStatisticServiceImpl implements SalesStatisticService {

    @Resource
    private FollowupRepository followupRepository;

    @Resource
    private SalesleadRepository salesleadRepository;

    @Override
    // 统计跟进数量
    public SalesStatisticFollowupResp countFollowup(SalesStatisticReqVO reqVO) {
        Integer followupCount = followupRepository.countByCreateTimeBetweenAndCreatorIn(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds());
        SalesStatisticFollowupResp resp = new SalesStatisticFollowupResp();
        resp.setFollowupCount(followupCount);
        return resp;
    }

    @Override
    // 统计商机数量
    public SalesStatisticSalesleadResp countSaleslead(SalesStatisticReqVO reqVO) {
        Integer totalCount = salesleadRepository.countByCreateTimeBetweenAndCreatorIn(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds());
        Integer focusCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.KeyFocus.getStatus()));
        Integer quotedCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.IS_QUOTATION.getStatus()));
        Integer dealCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.ToProject.getStatus()));
        Integer lostCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.LostDeal.getStatus()));
        SalesStatisticSalesleadResp resp = new SalesStatisticSalesleadResp();
        resp.setTotalCount(totalCount);
        resp.setFocusCount(focusCount);
        resp.setQuotedCount(quotedCount);
        resp.setDealCount(dealCount);
        resp.setLostCount(lostCount);
        return resp;
    }
}
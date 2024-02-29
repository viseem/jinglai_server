package cn.iocoder.yudao.module.jl.service.statistic.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.*;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.enums.SalesLeadStatusEnums;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.crm.FollowupRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.salesgroupmember.SalesGroupMemberRepository;
import cn.iocoder.yudao.module.jl.repository.subjectgroupmember.SubjectGroupMemberRepository;
import cn.iocoder.yudao.module.jl.service.salesgroupmember.SalesGroupMemberServiceImpl;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private SubjectGroupMemberServiceImpl subjectGroupMemberService;

    @Resource
    private SalesGroupMemberServiceImpl salesGroupMemberService;

    @Resource
    private ContractFundLogRepository contractFundLogRepository;

    @Resource
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;

    @Override
    // 统计跟进数量
    public SalesStatisticFollowupResp countFollowup(SalesStatisticReqVO reqVO) {
        Integer followupCount = 0;
        if(reqVO.getSubjectGroupId()!=null){
            //把返回的List中的id取出来
            reqVO.setUserIds(subjectGroupMemberService.findMembersUserIdsByGroupId(reqVO.getSubjectGroupId()));
        }

        if(reqVO.getTimeRange()!=null){
            reqVO.setStartTime(StatisticUtils.getStartTimeByTimeRange(reqVO.getTimeRange()));
        }

        //打印数组,打印成字符串
//        System.out.println(Arrays.toString(reqVO.getUserIds()));

        followupCount= followupRepository.countByCreateTimeBetweenAndCreatorIn(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds());
        SalesStatisticFollowupResp resp = new SalesStatisticFollowupResp();
        resp.setFollowupCount(followupCount);
        return resp;
    }

    @Override
    // 统计商机数量
    public SalesStatisticSalesleadResp countSaleslead(SalesStatisticReqVO reqVO) {

        if(reqVO.getSubjectGroupId()!=null){
            //把返回的List中的id取出来
            reqVO.setUserIds(subjectGroupMemberService.findMembersUserIdsByGroupId(reqVO.getSubjectGroupId()));
        }

        if(reqVO.getMonth()!=null){
            reqVO.setStartTime(StatisticUtils.getStartTimeByTimeRange(reqVO.getTimeRange()));
        }
        Integer totalCount = salesleadRepository.countByCreateTimeBetweenAndCreatorIn(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds());
        Integer focusCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.KeyFocus.getStatus()));
        Integer quotedCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.IS_QUOTATION.getStatus()));
        Integer dealCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.ToProject.getStatus()));
        Integer lostCount = salesleadRepository.countByCreateTimeBetweenAndCreatorInAndStatus(reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds(), Integer.valueOf(SalesLeadStatusEnums.LostDeal.getStatus()));
        SalesStatisticSalesleadResp resp = new SalesStatisticSalesleadResp();
        resp.setSalesleadCount(totalCount);
        resp.setFocusCount(focusCount);
        resp.setQuotedCount(quotedCount);
        resp.setDealCount(dealCount);
        resp.setLostCount(lostCount);
        return resp;
    }

    @Override
    public SalesGroupStatisticResp groupStats(SalesGroupStatisticReqVO reqVO) {
        SalesGroupStatisticResp resp = new SalesGroupStatisticResp();

        List<SalesGroupMember> groupMemberList = new ArrayList<>();
        if(reqVO.getGroupIds()!=null){
            //把返回的List中的id取出来
            groupMemberList = salesGroupMemberService.findMembersUserInGroupIds(reqVO.getGroupIds());
            reqVO.setUserIds(groupMemberList.stream().map(SalesGroupMember::getUserId).toArray(Long[]::new));
        }

        if(reqVO.getMonth()!=null){
            LocalDateTime[] startAndEndTimeByMonth = StatisticUtils.getStartAndEndTimeByMonth(reqVO.getMonth());
            reqVO.setStartTime(startAndEndTimeByMonth[0]);
            reqVO.setEndTime(startAndEndTimeByMonth[1]);
        }

        if(!groupMemberList.isEmpty()){
            List<ContractFundLog> contractFundLogList = contractFundLogRepository.findByStatusAndPaidTimeBetweenAndSalesIdIn(ContractFundStatusEnums.AUDITED.getStatus(), reqVO.getStartTime(), reqVO.getEndTime(), List.of(reqVO.getUserIds()));

            List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.findByCreatorInAndSignedTimeBetweenAndStatus(reqVO.getUserIds(), reqVO.getStartTime(), reqVO.getEndTime(), ProjectContractStatusEnums.SIGNED.getStatus());
            for (SalesGroupMember member : groupMemberList) {
                for (ContractFundLog contractFundLog : contractFundLogList) {
                    if(contractFundLog.getReceivedPrice() != null&&contractFundLog.getSalesId().equals(member.getUserId())) {
                        member.setRefundAmount(
                                member.getRefundAmount().add(contractFundLog.getReceivedPrice())
                        );
                    }
                }
            }

            for (ProjectConstractOnly contract : contractList) {
                for (SalesGroupMember member : groupMemberList) {
                    if(contract.getCreator().equals(member.getUserId())) {
                        if(contract.getPrice() != null) {
                            member.setOrderAmount(
                                    member.getOrderAmount().add(contract.getPrice())
                            );
                        }
                    }
                }
            }


        }
        resp.setSalesGroupMemberList(groupMemberList);

        return resp;
    }

    @Override
    public SalesGroupStatisticResp groupStatsNotPay(SalesGroupStatisticReqVO reqVO) {
        SalesGroupStatisticResp resp = new SalesGroupStatisticResp();

        List<SalesGroupMember> groupMemberList = new ArrayList<>();
        if(reqVO.getGroupIds()!=null){
            //把返回的List中的id取出来
            groupMemberList = salesGroupMemberService.findMembersUserInGroupIds(reqVO.getGroupIds());
            reqVO.setUserIds(groupMemberList.stream().map(SalesGroupMember::getUserId).toArray(Long[]::new));
        }

        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.findByStatusAndSalesIdIn(ProjectContractStatusEnums.SIGNED.getStatus(), reqVO.getUserIds());
        List<ContractInvoiceLog> invoiceLogList = contractInvoiceLogRepository.findByPriceStatusAndSalesIdIn(ContractInvoiceStatusEnums.RECEIVED_NO.getStatus(), reqVO.getUserIds());

        for (SalesGroupMember member : groupMemberList) {
            for (ProjectConstractOnly contract : contractList) {
                if(contract.getCreator().equals(member.getUserId())) {
                    /*if(contract.getPrice() != null) {
                        member.setOrderAmount(
                                member.getOrderAmount().add(contract.getPrice())
                        );
                    }

                    if(contract.getReceivedPrice() != null) {
                        member.setRefundAmount(
                                member.getRefundAmount().add(contract.getReceivedPrice())
                        );
                    }*/
                    if(contract.getPrice()!=null){
                        if(contract.getReceivedPrice()==null){
                            contract.setReceivedPrice(BigDecimal.ZERO);
                        }
                        member.setAllNeedReceiveAmount(
                                member.getAllNeedReceiveAmount().add(contract.getPrice().subtract(contract.getReceivedPrice()))
                        );
                    }


                }
            }

            for (ContractInvoiceLog contractInvoiceLog : invoiceLogList) {
                if(contractInvoiceLog.getSalesId().equals(member.getUserId())&&contractInvoiceLog.getPriceStatus().equals(ContractInvoiceStatusEnums.RECEIVED_NO.getStatus())) {
                    member.setNotPayInvoiceAmount(
                            member.getNotPayInvoiceAmount().add(contractInvoiceLog.getReceivedPrice())
                    );
                }
            }
        }

        resp.setSalesGroupMemberList(groupMemberList);

        return resp;
    }
}
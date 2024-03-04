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
import cn.iocoder.yudao.module.jl.service.salesgroupmember.SalesGroupMemberServiceImpl;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

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


    public static <T extends Serializable> List<SalesGroupMember> deepCopy(List<SalesGroupMember> original) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            // 将原始列表写入字节流
            oos.writeObject(original);

            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                // 从字节流中读取新的对象
                return (List<SalesGroupMember>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SalesGroupStatisticResp groupStatsOrder(SalesGroupStatisticReqVO reqVO) {
        SalesGroupStatisticResp resp = new SalesGroupStatisticResp();

        List<SalesGroupMember> groupMemberList = new ArrayList<>();
        if(reqVO.getGroupIds()!=null){
            //把返回的List中的id取出来
            groupMemberList = salesGroupMemberService.findMembersUserInGroupIds(reqVO.getGroupIds());
            reqVO.setUserIds(groupMemberList.stream().map(SalesGroupMember::getUserId).toArray(Long[]::new));
        }
        resp.setSalesGroupMemberList(groupMemberList);

        if(reqVO.getMonth()!=null){
            LocalDateTime[] startAndEndTimeByMonth = StatisticUtils.getStartAndEndTimeByMonth(reqVO.getMonth());
            reqVO.setStartTime(startAndEndTimeByMonth[0]);
            reqVO.setEndTime(startAndEndTimeByMonth[1]);
        }

        if(!groupMemberList.isEmpty()){
//            List<ContractFundLog> contractFundLogList = contractFundLogRepository.findByStatusAndPaidTimeBetweenAndSalesIdIn(ContractFundStatusEnums.AUDITED.getStatus(), reqVO.getStartTime(), reqVO.getEndTime(), List.of(reqVO.getUserIds()));

            List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.findByCreatorInAndSignedTimeBetweenAndStatus(reqVO.getUserIds(), reqVO.getStartTime(), reqVO.getEndTime(), ProjectContractStatusEnums.SIGNED.getStatus());
/*            for (SalesGroupMember member : resp.getSalesGroupMemberList()) {
                for (ContractFundLog contractFundLog : contractFundLogList) {
                    if(contractFundLog.getReceivedPrice() != null&&contractFundLog.getSalesId().equals(member.getUserId())) {
                        member.setRefundAmount(
                                member.getRefundAmount().add(contractFundLog.getReceivedPrice())
                        );
                    }
                }
            }*/

            for (SalesGroupMember member : resp.getSalesGroupMemberList()) {
                for (ProjectConstractOnly contract : contractList) {
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
        sortAndRank(resp.getSalesGroupMemberList(), "desc", SalesGroupMember::getOrderAmount, SalesGroupMember::setOrderAmountRank);
        return resp;
    }

    @Override
    public SalesGroupStatisticResp groupStatsRefund(SalesGroupStatisticReqVO reqVO) {
        SalesGroupStatisticResp resp = new SalesGroupStatisticResp();

        List<SalesGroupMember> groupMemberList = new ArrayList<>();
        if(reqVO.getGroupIds()!=null){
            //把返回的List中的id取出来
            groupMemberList = salesGroupMemberService.findMembersUserInGroupIds(reqVO.getGroupIds());
            reqVO.setUserIds(groupMemberList.stream().map(SalesGroupMember::getUserId).toArray(Long[]::new));
        }
        resp.setSalesGroupMemberList(groupMemberList);

        if(reqVO.getMonth()!=null){
            LocalDateTime[] startAndEndTimeByMonth = StatisticUtils.getStartAndEndTimeByMonth(reqVO.getMonth());
            reqVO.setStartTime(startAndEndTimeByMonth[0]);
            reqVO.setEndTime(startAndEndTimeByMonth[1]);
        }

        if(!groupMemberList.isEmpty()){
            List<ContractFundLog> contractFundLogList = contractFundLogRepository.findByStatusAndPaidTimeBetweenAndSalesIdIn(ContractFundStatusEnums.AUDITED.getStatus(), reqVO.getStartTime(), reqVO.getEndTime(), List.of(reqVO.getUserIds()));

//            List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.findByCreatorInAndSignedTimeBetweenAndStatus(reqVO.getUserIds(), reqVO.getStartTime(), reqVO.getEndTime(), ProjectContractStatusEnums.SIGNED.getStatus());
            for (SalesGroupMember member : resp.getSalesGroupMemberList()) {
                for (ContractFundLog contractFundLog : contractFundLogList) {
                    if(contractFundLog.getReceivedPrice() != null&&contractFundLog.getSalesId().equals(member.getUserId())) {
                        member.setRefundAmount(
                                member.getRefundAmount().add(contractFundLog.getReceivedPrice())
                        );
                    }
                }
            }

/*            for (SalesGroupMember member : resp.getSalesGroupMemberList()) {
                for (ProjectConstractOnly contract : contractList) {
                    if(contract.getCreator().equals(member.getUserId())) {
                        if(contract.getPrice() != null) {
                            member.setOrderAmount(
                                    member.getOrderAmount().add(contract.getPrice())
                            );
                        }
                    }
                }
            }*/
        }
        sortAndRank(resp.getSalesGroupMemberList(), "desc", SalesGroupMember::getRefundAmount, SalesGroupMember::setRefundAmountRank);
        return resp;
    }

    // 泛型方法：按照指定字段进行排序并赋予名次
    private static <T, R extends Comparable<? super R>> void sortAndRank(List<T> items, String sortOrder,
                                                                         Function<T, R> sortBy, BiConsumer<T, Integer> rankBy) {
        // 使用Comparator进行排序
        Comparator<T> comparator;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = Comparator.comparing(sortBy).reversed();
        } else if ("asc".equalsIgnoreCase(sortOrder)) {
            comparator = Comparator.comparing(sortBy);
        } else {
            throw new IllegalArgumentException("Invalid sortOrder. Use 'asc' or 'desc'.");
        }
        Collections.sort(items, comparator);

        // 赋予名次
        Integer rank = 1;
        for (int i = 0; i < items.size(); i++) {
            T currentItem = items.get(i);
            if (i > 0) {
                T previousItem = items.get(i - 1);
                if (comparator.compare(currentItem, previousItem) != 0) {
                    rank = i + 1;  // 如果字段值不同，更新名次
                }
            }
            // 设置名次
            rankBy.accept(currentItem, rank);
        }
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
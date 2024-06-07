package cn.iocoder.yudao.module.jl.service.statistic.financial;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.financial.FinancialReceivableStatisticReqVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 专题小组 Service 实现类
 *
 */
@Service
@Validated
public class FinancialStatisticServiceImpl implements FinancialStatisticService {

    @Resource
    private SubjectGroupMemberServiceImpl subjectGroupMemberService;

    @Resource
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private ContractInvoiceLogOnlyRepository contractInvoiceLogOnlyRepository;

    @Resource
    private ContractFundLogOnlyRepository contractFundLogOnlyRepository;

    @Override
    public FinancialStatisticResp accountsReceivable(FinancialReceivableStatisticReqVO reqVO) {
        FinancialStatisticResp resp = new FinancialStatisticResp();

        if(reqVO.getSubjectGroupId()!=null){
            //把返回的List中的id取出来
            reqVO.setUserIds(subjectGroupMemberService.findMembersUserIdsByGroupId(reqVO.getSubjectGroupId()));
        }

        if(reqVO.getTimeRange()!=null){
            reqVO.setStartTime(StatisticUtils.getStartTimeByTimeRange(reqVO.getTimeRange()));
        }


        System.out.println(reqVO.getStartTime()+"====="+reqVO.getEndTime());

        // 查询数据
        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.getContractFinancialStatistic(reqVO.getUserIds(), ProjectContractStatusEnums.SIGNED.getStatus(), reqVO.getStartTime(), reqVO.getEndTime());
        // 遍历 contract list, 求和应收金额，已收金额，已开票金额
        for (ProjectConstractOnly contract : contractList) {
            System.out.println("contract---"+contract+contract.getPrice());
            if(contract.getReceivedPrice() != null) {
                resp.setContractPaymentAmount(resp.getContractPaymentAmount().add(contract.getReceivedPrice()));
            }
            if(contract.getPrice() != null) {
                resp.setOrderAmount(resp.getOrderAmount().add(contract.getPrice()));
            }
        }
//ContractFundStatusEnums.AUDITED.getStatus(),
        contractInvoiceLogOnlyRepository.findByStatusNotAndPaidTimeBetweenAndSalesIdIn( ContractInvoiceStatusEnums.NOT_INVOICE.getStatus(),reqVO.getStartTime(), reqVO.getEndTime(), reqVO.getUserIds()).forEach(contractInvoiceLog -> {
            resp.setInvoiceAmount(resp.getInvoiceAmount().add(contractInvoiceLog.getReceivedPrice()));
        });


        //查询contractFundLog表，获取已收金额
        contractFundLogOnlyRepository.findByStatusAndPaidTimeBetweenAndSalesIdIn(ContractFundStatusEnums.AUDITED.getStatus(), reqVO.getStartTime(), reqVO.getEndTime(), List.of(reqVO.getUserIds())).forEach(contractFundLog -> {
            resp.setPaymentAmount(resp.getPaymentAmount().add(contractFundLog.getReceivedPrice()));
        });

        //减去
        resp.setAccountsReceivable(
                resp.getOrderAmount().subtract(resp.getContractPaymentAmount())
        );

        resp.setContractIds(contractList.stream().map(ProjectConstractOnly::getId).collect(Collectors.toList()));
        return resp;
    }

    @Override
    public List<FinancialStatisticResp> accountsReceivableMonth(FinancialReceivableStatisticReqVO reqVO) {

        List<FinancialStatisticResp> respList = new ArrayList<>();

        if(reqVO.getSubjectGroupId()!=null){
            //把返回的List中的id取出来
            reqVO.setUserIds(subjectGroupMemberService.findMembersUserIdsByGroupId(reqVO.getSubjectGroupId()));
        }

        reqVO.setMonth(null);

        //按照月份遍历今年的每个月,查询数据
        for (int i = 1; i <= 12; i++) {
            //大于当前月份的不统计
            if(i>LocalDateTime.now().getMonthValue()){
                break;
            }
            LocalDateTime[] startAndEndTimeByMonth = StatisticUtils.getStartAndEndTimeByMonth(i);
            reqVO.setStartTime(startAndEndTimeByMonth[0]);
            reqVO.setEndTime(startAndEndTimeByMonth[1]);
            FinancialStatisticResp financialStatisticResp = accountsReceivable(reqVO);
            respList.add(financialStatisticResp);
        }

        return respList;

    }
}
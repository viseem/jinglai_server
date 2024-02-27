package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialAllStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.financial.FinancialAllStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales.SalesStatisticSalesleadResp;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.service.statistic.StatisticUtils;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 财务数据统计")
@RestController
@RequestMapping("/statistics/financial")
@Validated
public class FinancialStatisticController {

    // 合同 Repository
    @Resource
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private SubjectGroupMemberService subjectGroupMemberService;

    @Resource
    private ContractFundLogRepository contractFundLogRepository;

    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;

    @GetMapping("/accounts-receivable-all")
    @Operation(summary = "全部应收款统计")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<FinancialAllStatisticResp> getFinancialStatisticAll(@Valid FinancialAllStatisticReqVO reqVO) {
        FinancialAllStatisticResp resp = new FinancialAllStatisticResp();
        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.findByStatusAndSalesIdIn(ProjectContractStatusEnums.SIGNED.getStatus(), reqVO.getUserIds());
        for (ProjectConstractOnly contract : contractList) {
            if(contract.getReceivedPrice() != null) {
                resp.setAllContractPaymentAmount(resp.getAllContractPaymentAmount().add(contract.getReceivedPrice()));
            }
            if(contract.getPrice() != null) {
                resp.setAllOrderAmount(resp.getAllOrderAmount().add(contract.getPrice()));
            }
        }
        resp.setAllAccountsReceivable(
                resp.getAllOrderAmount().subtract(resp.getAllContractPaymentAmount())
        );

        List<ContractInvoiceLog> invoiceLogList = contractInvoiceLogRepository.findByPriceStatusAndSalesIdIn(ContractInvoiceStatusEnums.RECEIVED_NO.getStatus(), reqVO.getUserIds());

        for (ContractInvoiceLog contractInvoiceLog : invoiceLogList) {
            resp.setAllUnreceivedInvoiceAmount(resp.getAllUnreceivedInvoiceAmount().add(contractInvoiceLog.getReceivedPrice()));
        }

        return success(resp);
    }

    @GetMapping("/accounts-receivable")
    @Operation(summary = "应收款统计")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<FinancialStatisticResp> getFinancialStatistic (
            @RequestParam(name = "startTime", required = false) Long startTime,
            @RequestParam(name = "endTime", required = false) Long endTime,
            @RequestParam(name = "timeRange", required = false) String timeRange,
            @RequestParam(name = "userIds", required = false, defaultValue = "") List<Long> userIds,
            @RequestParam(name = "subjectGroupId", required = false) Long subjectGroupId,
            @RequestParam(name = "userId", required = false) Long userId
    ) {
        // 获取请求的数据

        LocalDateTime localDateEndTime = LocalDateTime.now();
        LocalDateTime localDateStartTime = LocalDateTime.now();
        if(endTime != null) {
            // 今天的最后一分一秒
            Instant instant = Instant.ofEpochMilli(endTime); // 将时间戳转换为Instant对象
            ZoneId zoneId = ZoneId.systemDefault(); // 获取本地时区
            localDateEndTime = LocalDateTime.ofInstant(instant, zoneId);
            localDateEndTime = localDateEndTime.withHour(23).withMinute(59).withSecond(59);
        }

        if(startTime == null) {
            // startTime 本周的第一天
            localDateStartTime = localDateEndTime.minusDays(localDateEndTime.getDayOfWeek().getValue());
        } else {
            Instant instant = Instant.ofEpochMilli(startTime); // 将时间戳转换为Instant对象
            ZoneId zoneId = ZoneId.systemDefault(); // 获取本地时区
            localDateStartTime = LocalDateTime.ofInstant(instant, zoneId);
            localDateStartTime = localDateStartTime.withHour(23).withMinute(59).withSecond(59);
        }

        if(timeRange!=null){
            localDateStartTime = StatisticUtils.getStartTimeByTimeRange(timeRange);
        }

        if(userId != null) {
            userIds.add(userId);
        }

        if (subjectGroupId != null) {
            // 查找groupId下的所有人员
            subjectGroupMemberService.findMembersByGroupId(subjectGroupId).forEach(member -> userIds.add(member.getUserId()));
        }
        // 查询数据
        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.getContractFinancialStatistic(userIds, ProjectContractStatusEnums.SIGNED.getStatus(), localDateStartTime, localDateEndTime);
        // 遍历 contract list, 求和应收金额，已收金额，已开票金额
        FinancialStatisticResp resp = new FinancialStatisticResp();
        for (ProjectConstractOnly contract : contractList) {
            if(contract.getReceivedPrice() != null) {
                resp.setContractPaymentAmount(resp.getContractPaymentAmount().add(contract.getReceivedPrice()));
            }
            if(contract.getPrice() != null) {
                resp.setOrderAmount(resp.getOrderAmount().add(contract.getPrice()));
            }
            if (contract.getInvoicedPrice() != null) {
                resp.setInvoiceAmount(resp.getInvoiceAmount().add(contract.getInvoicedPrice()));
            }
        }

        //查询contractFundLog表，获取已收金额
        contractFundLogRepository.findByStatusAndPaidTimeBetweenAndSalesIdIn(ContractFundStatusEnums.AUDITED.getStatus(), localDateStartTime, localDateEndTime, userIds).forEach(contractFundLog -> {
            resp.setPaymentAmount(resp.getPaymentAmount().add(contractFundLog.getReceivedPrice()));
        });

        //减去
        resp.setAccountsReceivable(
                resp.getOrderAmount().subtract(resp.getContractPaymentAmount())
        );

        resp.setContractIds(contractList.stream().map(ProjectConstractOnly::getId).collect(Collectors.toList()));

        return success(resp);
    }
}

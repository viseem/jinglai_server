package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialStatisticResp;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
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
                resp.setPaymentAmount(resp.getPaymentAmount().add(contract.getReceivedPrice()));
            }
            if(contract.getPrice() != null) {
                resp.setOrderAmount(resp.getOrderAmount().add(contract.getPrice()));
            }
            if (contract.getInvoicedPrice() != null) {
                resp.setInvoiceAmount(resp.getInvoiceAmount().add(contract.getInvoicedPrice()));
            }
        }
        //减去
        resp.setAccountsReceivable(
                resp.getOrderAmount().subtract(resp.getPaymentAmount())
        );

        resp.setContractIds(contractList.stream().map(ProjectConstractOnly::getId).collect(Collectors.toList()));

        return success(resp);
    }
}

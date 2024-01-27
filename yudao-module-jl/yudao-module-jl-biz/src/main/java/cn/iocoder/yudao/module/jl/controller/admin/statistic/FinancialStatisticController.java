package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialStatisticResp;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Tag(name = "管理后台 - 财务数据统计")
@RestController
@RequestMapping("/statistics/financial")
@Validated
public class FinancialStatisticController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 合同 Repository
    @Resource
    private ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    private SubjectGroupMemberService subjectGroupMemberService;

    @GetMapping("/accounts-receivable")
    @Operation(summary = "应收款统计")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<FinancialStatisticResp> getFinancialStatistic (
            @RequestParam(name = "startTime", required = false) @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) LocalDateTime startTime,
            @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) LocalDateTime endTime,
            @RequestParam(name = "timeRange", required = false) String timeRange,
            @RequestParam(name = "userIds", required = false, defaultValue = "") List<Long> userIds,
            @RequestParam(name = "subjectGroupId", required = false) Long subjectGroupId,
            @RequestParam(name = "userId", required = false) Long userId
    ) {
        // 获取请求的数据
        logger.info("getFinancialStatistic startTime = {}, endTime = {}, userIds = {}, subjectGroupId = {}, userId = {}",
                startTime, endTime, userIds, subjectGroupId, userId);

        if(endTime == null) {
            // 今天的最后一分一秒
            endTime = LocalDateTime.now();
            endTime = endTime.withHour(23).withMinute(59).withSecond(59);
        }

        if(startTime == null) {
            // startTime 本周的第一天
            startTime = endTime.minusDays(endTime.getDayOfWeek().getValue());
        }

        if(timeRange != null) {
            switch (timeRange) {
                case "week":
                    // startTime 本周的第一天
                    startTime = endTime.minusDays(endTime.getDayOfWeek().getValue());
                    break;
                case "month":
                    // startTime 本月的第一天
                    startTime = endTime.minusDays(endTime.getDayOfMonth());
                    break;
                case "year":
                    // startTime 本年的第一天
                    startTime = endTime.minusDays(endTime.getDayOfYear());
                    break;
                default:
                    break;
            }
        }

        if(userId != null) {
            userIds.add(userId);
        }

        if (subjectGroupId != null) {
            // 查找groupId下的所有人员
            subjectGroupMemberService.findMembersByGroupId(subjectGroupId).forEach(member -> userIds.add(member.getUserId()));
        }

        // 查询数据
        List<ProjectConstractOnly> contractList = projectConstractOnlyRepository.getContractFinancialStatistic(userIds, ProjectContractStatusEnums.SIGNED.getStatus(), startTime, endTime);
        // 遍历 contract list, 求和应收金额，已收金额，已开票金额
        FinancialStatisticResp resp = new FinancialStatisticResp();
        for (ProjectConstractOnly contract : contractList) {
            if(contract.getReceivedPrice() != null) {
                resp.setPaymentAmount(resp.getPaymentAmount().add(contract.getReceivedPrice()));
            }
            if(contract.getPrice() != null) {
                resp.setAccountsReceivable(resp.getAccountsReceivable().add(contract.getPrice()));
            }
            if (contract.getInvoicedPrice() != null) {
                resp.setInvoiceAmount(resp.getInvoiceAmount().add(contract.getInvoicedPrice()));
            }
        }

        return success(resp);
    }
}

package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialAllStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.FinancialStatisticResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.financial.FinancialAllStatisticReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.financial.FinancialReceivableStatisticReqVO;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.enums.ContractInvoiceStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectContractStatusEnums;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractOnlyRepository;
import cn.iocoder.yudao.module.jl.service.statistic.financial.FinancialStatisticServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
    private FinancialStatisticServiceImpl financialStatisticService;

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

        List<ContractInvoiceLog> invoiceLogList = contractInvoiceLogRepository.findByStatusAndSalesIdIn(ContractInvoiceStatusEnums.RECEIVED_NO.getStatus(), reqVO.getUserIds());

        for (ContractInvoiceLog contractInvoiceLog : invoiceLogList) {
            resp.setAllUnreceivedInvoiceAmount(resp.getAllUnreceivedInvoiceAmount().add(contractInvoiceLog.getReceivedPrice()));
        }

        return success(resp);
    }


    /*
    *
    * @RequestParam(name = "startTime", required = false) Long startTime,
            @RequestParam(name = "endTime", required = false) Long endTime,
            @RequestParam(name = "timeRange", required = false) String timeRange,
            @RequestParam(name = "userIds", required = false, defaultValue = "") List<Long> userIds,
            @RequestParam(name = "subjectGroupId", required = false) Long subjectGroupId,
            @RequestParam(name = "userId", required = false) Long userId
    * */
    @GetMapping("/accounts-receivable")
    @Operation(summary = "应收款统计")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<FinancialStatisticResp> getFinancialStatistic (
            @Valid FinancialReceivableStatisticReqVO reqVO
            ) {
        // 获取请求的数据
        FinancialStatisticResp financialStatisticResp = financialStatisticService.accountsReceivable(reqVO);
        return success(financialStatisticResp);
    }

    @GetMapping("/accounts-receivable-month")
    @Operation(summary = "应收款统计")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<List<FinancialStatisticResp>> getFinancialStatisticMonth (
            @Valid FinancialReceivableStatisticReqVO reqVO
    ) {
        // 获取请求的数据
        List<FinancialStatisticResp> financialStatisticResps = financialStatisticService.accountsReceivableMonth(reqVO);
        return success(financialStatisticResps);
    }
}
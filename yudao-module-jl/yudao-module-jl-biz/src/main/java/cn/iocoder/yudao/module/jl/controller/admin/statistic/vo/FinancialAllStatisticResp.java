package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class FinancialAllStatisticResp {
    // 订单金额
    BigDecimal allOrderAmount = BigDecimal.ZERO;
    // 合同上面的回款金额
    BigDecimal allContractPaymentAmount = BigDecimal.ZERO;
    // 全部应收款 = 全部订单金额 - 合同上面的回款金额
    BigDecimal allAccountsReceivable = BigDecimal.ZERO;

    // 全部未到账发票金额
    BigDecimal allUnreceivedInvoiceAmount = BigDecimal.ZERO;

    List<Long> contractIds;
}

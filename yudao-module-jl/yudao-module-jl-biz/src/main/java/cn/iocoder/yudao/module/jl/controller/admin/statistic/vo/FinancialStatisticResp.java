package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class FinancialStatisticResp {
    // 订单金额
    BigDecimal orderAmount = BigDecimal.ZERO;
    // 应收款 = 订单金额 - 合同上面的回款金额
    BigDecimal accountsReceivable = BigDecimal.ZERO;
    // 已开票数额
    BigDecimal invoiceAmount = BigDecimal.ZERO;
    // 合同上面的回款金额
    BigDecimal contractPaymentAmount = BigDecimal.ZERO;
    // contractFundLog回款金额
    BigDecimal paymentAmount = BigDecimal.ZERO;

    List<Long> contractIds;
}

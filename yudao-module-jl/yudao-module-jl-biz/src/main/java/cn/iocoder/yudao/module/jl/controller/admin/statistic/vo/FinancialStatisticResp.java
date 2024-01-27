package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class FinancialStatisticResp {
    // 应收款
    BigDecimal accountsReceivable = BigDecimal.ZERO;
    // 已开票数额
    BigDecimal invoiceAmount = BigDecimal.ZERO;
    // 回款金额
    BigDecimal paymentAmount = BigDecimal.ZERO;
}

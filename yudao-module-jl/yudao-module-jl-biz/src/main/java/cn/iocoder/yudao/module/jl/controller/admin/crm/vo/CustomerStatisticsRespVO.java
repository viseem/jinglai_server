package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 客户 Response VO")
@Data
@ToString(callSuper = true)
public class CustomerStatisticsRespVO {

    //成交次数
    @Schema(description = "成交次数")
    private Integer dealCount;
    //成交总额
    @Schema(description = "成交总额")
    private BigDecimal dealAmount;
    //款项总额
    @Schema(description = "款项总额")
    private BigDecimal fundAmount;
    //已付款项总额
    @Schema(description = "已付款项总额")
    private BigDecimal paidAmount;
    //欠款总额
    @Schema(description = "欠款总额")
    private BigDecimal arrearsAmount;

}

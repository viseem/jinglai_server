package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目款项更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectFundPaymentUpdateReqVO extends ProjectFundBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4485")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "支付时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "paidTime")
    @NotNull(message = "支付时间不能为空")
    private String paidTime;

    @Schema(description = "凭证地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "url")
    @NotNull(message = "凭证地址不能为空")
    private String receiptUrl;

    @Schema(description = "凭证名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "url")
    @NotNull(message = "凭证名称不能为空")
    private String receiptName;

    @Schema(description = "付款方", requiredMode = Schema.RequiredMode.REQUIRED, example = "付款方")
    @NotNull(message = "付款方不能为空")
    private String payer;

    @Schema(description = "收款方", requiredMode = Schema.RequiredMode.REQUIRED, example = "收款方")
    @NotNull(message = "收款方不能为空")
    private String payee;

    @Schema(description = "收款备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "收款备注")
    @NotNull(message = "收款备注不能为空")
    private String payMark;
}

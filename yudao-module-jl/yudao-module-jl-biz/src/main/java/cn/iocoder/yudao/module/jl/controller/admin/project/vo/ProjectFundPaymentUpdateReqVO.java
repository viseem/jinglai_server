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
@ToString(callSuper = true)
public class ProjectFundPaymentUpdateReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4485")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "支付状态(未支付，部分支付，完全支付)", example = "2")
    private String status;


    private String mark;

    @Schema(description = "支付时间", example = "2021-08-01 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime actualPaymentTime;

    @Schema(description = "支付金额", example = "1000")
    private Long actualPaymentAmount=0L;
}

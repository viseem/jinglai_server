package cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目结算 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectSettlementBaseVO {

    @Schema(description = "项目id")
    private Long projectId;

    @Schema(description = "报价id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23423")
    @NotNull(message = "报价id不能为空")
    private Long quotationId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "结算金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结算金额不能为空")
    private BigDecimal paidAmount;

    @Schema(description = "提醒日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime reminderDate;

    @Schema(description = "结算日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime paidDate;

    @Schema(description = "应收金额")
    private BigDecimal amount;

    @Schema(description = "备注")
    private String mark;

    private String status;

    @Schema(description = "变更明细", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更明细不能为空")
    private String jsonLogs;


    private BigDecimal prevAmount=BigDecimal.ZERO;

    private BigDecimal currentAmount=BigDecimal.ZERO;

}

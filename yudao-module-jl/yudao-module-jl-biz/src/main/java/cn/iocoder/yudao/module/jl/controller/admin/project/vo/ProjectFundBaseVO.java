package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.enums.ProjectFundEnums;
import com.google.errorprone.annotations.FormatMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目款项 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectFundBaseVO {

    @Schema(description = "合同 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "111")
    @NotNull(message = "合同 id不能为空")
    private Long contractId;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "资金额度", requiredMode = Schema.RequiredMode.REQUIRED, example = "23415")
    @NotNull(message = "资金额度不能为空")
    private Long price;

    @Schema(description = "项目 id")
    private Long projectId;

    @Schema(description = "客户 id")
    private Long customerId;
    @Schema(description = "支付状态(未支付，部分支付，完全支付)", example = "2")
    private String status= ProjectFundEnums.UNBILL_UNPAID.getStatus();

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "支付的截止时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deadline;

    @Schema(description = "支付的截止时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime actualPaymentTime;

    private String payMark;

}

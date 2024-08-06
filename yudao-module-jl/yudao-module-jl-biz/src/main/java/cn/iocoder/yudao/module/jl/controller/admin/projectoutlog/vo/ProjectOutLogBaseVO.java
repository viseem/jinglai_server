package cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * example 空 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectOutLogBaseVO {

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6093")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "报价id")
    private Long quotationId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "步骤", requiredMode = Schema.RequiredMode.REQUIRED, example = "6093")
    @NotNull(message = "步骤不能为空")
    private Integer step;

    private String stepJson;

    /**
     *
     */
    private String step1Json;

    /**
     *
     */
    private String step2Json;

    /**
     *
     */
    private String step3Json;

    /**
     *
     */
    private String step4Json;

}

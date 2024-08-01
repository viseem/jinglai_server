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

    @Schema(description = "内部数据评审json")
    private String dataSignJson;

    @Schema(description = "客户评价")
    private String customerComment;

    @Schema(description = "客户打分")
    private String customerScoreJson;

    @Schema(description = "报价金额", example = "10360")
    private BigDecimal quotationPrice;

    @Schema(description = "报价备注")
    private String quotationMark;

    @Schema(description = "已收金额", example = "14734")
    private BigDecimal receivedPrice;

    @Schema(description = "已收备注")
    private String receivedMark;

    @Schema(description = "合同金额", example = "11191")
    private BigDecimal contractPrice;

    @Schema(description = "合同备注")
    private String contractMark;

    @Schema(description = "材料成本", example = "11496")
    private BigDecimal supplyPrice;

    @Schema(description = "材料备注")
    private String supplyMark;

    @Schema(description = "委外成本", example = "2216")
    private BigDecimal outsourcePrice;

    @Schema(description = "委外备注")
    private String outsourceMark;

    @Schema(description = "客户签字链接", example = "https://www.iocoder.cn")
    private String customerSignImgUrl;

    @Schema(description = "客户签字时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime custoamerSignTime;

    @Schema(description = "步骤的确认日志")
    private String stepsJsonLog;

    @Schema(description = "出库金额，理论上等于已收金额", example = "12597")
    private BigDecimal resultPrice;

    @Schema(description = "出库金额备注")
    private String resultMark;

    /**
     * 数据链接
     */
    private String dataLink;

    /**
     * 数据备注
     */
    private String dataMark;


    private Integer step;

}

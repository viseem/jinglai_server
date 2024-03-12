package cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 拜访记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class VisitLogBaseVO {

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3189")
    @NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "销售id")
    private Long salesId;

    @Schema(description = "拜访时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime time;

    @Schema(description = "拜访地址")
    private String address;

    @Schema(description = "拜访目的")
    private String goal;

    @Schema(description = "拜访途径", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "拜访途径不能为空")
    private String visitType;

    @Schema(description = "拜访内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "拜访内容不能为空")
    private String content;

    @Schema(description = "反馈")
    private String feedback;

    @Schema(description = "评分")
    private Integer score;

    @Schema(description = "备注")
    private String mark;

}

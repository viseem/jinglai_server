package cn.iocoder.yudao.module.jl.controller.admin.projectservice.vo;

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
 * 项目售后 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectServiceBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11195")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验名目 id", example = "31297")
    private Long projectCategoryId;

    @Schema(description = "内部人员 id", example = "6574")
    private Long userId;

    @Schema(description = "客户 id", example = "6806")
    private Long customerId;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "反馈的内容")
    private String content;

    @Schema(description = "处理结果")
    private String result;

    @Schema(description = "处理人", example = "8720")
    private Long resultUserId;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime resultTime;

}

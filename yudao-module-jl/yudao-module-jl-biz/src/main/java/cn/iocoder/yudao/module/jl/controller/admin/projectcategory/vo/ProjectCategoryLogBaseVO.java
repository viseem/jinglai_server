package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目实验名目的操作日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectCategoryLogBaseVO {

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20158")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "实验名目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20158")
    @NotNull(message = "实验名目 id不能为空")
    private Long projectCategoryId;


    @Schema(description = "原实验名目 id")
    private Long categoryId;

    @Schema(description = "实验人员")
    private Long operatorId;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容不能为空")
    private String mark;

    @Schema(description = "评分")
    private String score;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime operateTime;
}

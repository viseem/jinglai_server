package cn.iocoder.yudao.module.jl.controller.admin.reminder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 晶莱提醒事项 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ReminderBaseVO {

    @Schema(description = "合同id", example = "26211")
    private Long contractId;

    @Schema(description = "项目id", example = "29941")
    private Long projectId;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容不能为空")
    private String content;

    @Schema(description = "截止日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "截止日期不能为空")
    private String deadline;

    @Schema(description = "状态", example = "1")
    private String status;

}

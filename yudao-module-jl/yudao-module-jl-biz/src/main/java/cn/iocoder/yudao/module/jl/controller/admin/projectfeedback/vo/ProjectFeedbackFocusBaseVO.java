package cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 晶莱项目反馈关注人员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectFeedbackFocusBaseVO {

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3368")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "项目反馈id", requiredMode = Schema.RequiredMode.REQUIRED, example = "32185")
    @NotNull(message = "项目反馈id不能为空")
    private Long projectFeedbackId;

}

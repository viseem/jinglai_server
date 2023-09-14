package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目实验方案 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectPlanBaseVO {

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12094")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20351")
    @NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "安排单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15994")
    @NotNull(message = "安排单id不能为空")
    private Long scheduleId;

    @Schema(description = "方案text", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "方案text不能为空")
    private String planText;

    @Schema(description = "版本", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本不能为空")
    private String version;

}

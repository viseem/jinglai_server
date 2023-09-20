package cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 任务关系依赖 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TaskRelationBaseVO {

    @Schema(description = "等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "等级不能为空")
    private Integer level;

    @Schema(description = "任务id：category sop", requiredMode = Schema.RequiredMode.REQUIRED, example = "8795")
    @NotNull(message = "任务id：category sop不能为空")
    private Long taskId;

    @Schema(description = "依赖id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9680")
    @NotNull(message = "依赖id不能为空")
    private Long dependId;

    @Schema(description = "依赖等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "9680")
    @NotNull(message = "依赖等级不能为空")
    private Integer dependLevel;

}

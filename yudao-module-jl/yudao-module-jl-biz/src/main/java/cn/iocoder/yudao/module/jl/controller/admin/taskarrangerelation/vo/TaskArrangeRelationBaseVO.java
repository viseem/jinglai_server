package cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 任务安排关系 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TaskArrangeRelationBaseVO {

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14875")
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    @Schema(description = "收费项id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10287")
    @NotNull(message = "收费项id不能为空")
    private Long chargeItemId;

    @Schema(description = "产品id", example = "25050")
    private Long productId;

    @Schema(description = "项目id", example = "25050")
    private Long projectId;

    @Schema(description = "报价id", example = "25050")
    private Long quotationId;

}

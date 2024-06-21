package cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 任务产品中间 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TaskProductBaseVO {

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4593")
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    @Schema(description = "产品id", requiredMode = Schema.RequiredMode.REQUIRED, example = "32020")
    @NotNull(message = "产品id不能为空")
    private Long productId;

}

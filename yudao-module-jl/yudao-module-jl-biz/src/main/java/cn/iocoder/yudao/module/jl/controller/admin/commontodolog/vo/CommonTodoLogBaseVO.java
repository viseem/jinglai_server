package cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 通用TODO记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CommonTodoLogBaseVO {

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容不能为空")
    private String content;

    @Schema(description = "实验任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5027")
    @NotNull(message = "实验任务id不能为空")
    private Long refId;

    @Schema(description = "todo类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "todo类型不能为空")
    private String type;

}

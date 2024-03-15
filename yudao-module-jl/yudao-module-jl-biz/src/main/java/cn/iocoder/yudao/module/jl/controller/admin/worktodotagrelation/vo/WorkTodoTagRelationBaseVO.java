package cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 工作任务 TODO 与标签的关联 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class WorkTodoTagRelationBaseVO {

    @Schema(description = "todo id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2824")
    @NotNull(message = "todo id不能为空")
    private Long todoId;

    @Schema(description = "tag id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12250")
    @NotNull(message = "tag id不能为空")
    private Long tagId;

}

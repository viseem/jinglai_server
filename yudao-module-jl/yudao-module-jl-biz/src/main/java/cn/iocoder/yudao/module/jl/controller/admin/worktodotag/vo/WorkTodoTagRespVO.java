package cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 工作任务 TODO 的标签 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoTagRespVO extends WorkTodoTagBaseVO {

    @Schema(description = "类型：系统通用/用户自己创建", example = "2")
    private String type;

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22012")
    @NotNull(message = "ID不能为空")
    private Long id;
}

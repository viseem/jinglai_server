package cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 工作任务 TODO 的标签更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoTagUpdateReqVO extends WorkTodoTagBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22012")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "类型：系统通用/用户自己创建", example = "2")
    private String type;

}

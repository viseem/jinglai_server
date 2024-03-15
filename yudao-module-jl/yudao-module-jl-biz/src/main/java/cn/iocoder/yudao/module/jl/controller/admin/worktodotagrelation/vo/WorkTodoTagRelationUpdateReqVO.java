package cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 工作任务 TODO 与标签的关联更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoTagRelationUpdateReqVO extends WorkTodoTagRelationBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8085")
    @NotNull(message = "ID不能为空")
    private Long id;

}

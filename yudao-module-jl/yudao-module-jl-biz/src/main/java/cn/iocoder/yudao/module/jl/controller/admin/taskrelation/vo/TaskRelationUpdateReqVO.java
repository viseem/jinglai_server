package cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 任务关系依赖更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskRelationUpdateReqVO extends TaskRelationBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8402")
    @NotNull(message = "ID不能为空")
    private Long id;

}

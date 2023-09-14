package cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 晶莱项目反馈关注人员更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectFeedbackFocusUpdateReqVO extends ProjectFeedbackFocusBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10752")
    @NotNull(message = "ID不能为空")
    private Long id;

}

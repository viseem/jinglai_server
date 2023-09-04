package cn.iocoder.yudao.module.jl.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 晶莱反馈更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackUpdateReqVO extends FeedbackBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17249")
    @NotNull(message = "ID不能为空")
    private Long id;

}

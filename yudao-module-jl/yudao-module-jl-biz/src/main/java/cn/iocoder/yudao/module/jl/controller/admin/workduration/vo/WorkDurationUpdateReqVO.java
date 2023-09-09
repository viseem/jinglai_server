package cn.iocoder.yudao.module.jl.controller.admin.workduration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 工时更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkDurationUpdateReqVO extends WorkDurationBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4516")
    @NotNull(message = "ID不能为空")
    private Long id;

}

package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 通用任务更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommonTaskUpdateReqVO extends CommonTaskBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17096")
    @NotNull(message = "ID不能为空")
    private Long id;

}

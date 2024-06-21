package cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 任务产品中间更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskProductUpdateReqVO extends TaskProductBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29570")
    @NotNull(message = "ID不能为空")
    private Long id;

}

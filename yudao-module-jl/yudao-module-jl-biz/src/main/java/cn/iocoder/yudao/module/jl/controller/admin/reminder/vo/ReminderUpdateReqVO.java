package cn.iocoder.yudao.module.jl.controller.admin.reminder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 晶莱提醒事项更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReminderUpdateReqVO extends ReminderBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7206")
    @NotNull(message = "ID不能为空")
    private Long id;

}

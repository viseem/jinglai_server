package cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 晶莱到访预约更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VisitAppointmentUpdateReqVO extends VisitAppointmentBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18948")
    @NotNull(message = "ID不能为空")
    private Long id;

}

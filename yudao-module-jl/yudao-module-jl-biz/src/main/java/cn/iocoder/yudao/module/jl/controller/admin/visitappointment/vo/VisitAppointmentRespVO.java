package cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo;

import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 晶莱到访预约 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VisitAppointmentRespVO extends VisitAppointmentBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18948")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private AssetDeviceLog deviceLog;
}

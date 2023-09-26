package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 公司资产（设备）预约 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssetDeviceLogRespVO extends AssetDeviceLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28995")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private AssetDevice device;

    private ProjectSimple project;

}

package cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo;

import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - PI组协作 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PiCollaborationRespVO extends PiCollaborationBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24527")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private Saleslead SALESLEAD;

    private ProjectOnly PROJECT;
}

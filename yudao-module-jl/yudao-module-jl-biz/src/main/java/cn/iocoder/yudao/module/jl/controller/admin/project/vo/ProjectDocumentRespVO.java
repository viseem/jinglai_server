package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 项目文档 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectDocumentRespVO extends ProjectDocumentBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5394")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private ProjectSimple project;

    private User user;

}

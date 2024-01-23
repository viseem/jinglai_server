package cn.iocoder.yudao.module.jl.controller.admin.workduration.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 工时 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkDurationRespVO extends WorkDurationBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4516")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private User user;
    private User auditUser;
    private ProjectSimple project;
    private ProjectCategoryOnly projectCategory;

}

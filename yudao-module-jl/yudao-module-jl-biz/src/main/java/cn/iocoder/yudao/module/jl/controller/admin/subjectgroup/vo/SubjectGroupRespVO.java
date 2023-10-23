package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 专题小组 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectGroupRespVO extends SubjectGroupBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "594")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}

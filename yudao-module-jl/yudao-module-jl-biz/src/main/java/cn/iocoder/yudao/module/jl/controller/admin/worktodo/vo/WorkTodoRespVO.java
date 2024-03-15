package cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 工作任务 TODO Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoRespVO extends WorkTodoBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8372")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "状态：未受理、已处理", example = "2")
    private String status;

    @Schema(description = "类型(系统生成的任务、用户自己创建)", example = "1")
    private String type;

}

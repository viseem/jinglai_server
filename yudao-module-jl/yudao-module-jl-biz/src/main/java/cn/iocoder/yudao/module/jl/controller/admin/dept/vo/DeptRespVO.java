package cn.iocoder.yudao.module.jl.controller.admin.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 部门 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptRespVO extends DeptBaseVO {

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12587")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}

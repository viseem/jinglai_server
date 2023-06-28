package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 实验室人员 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LaboratoryUserRespVO extends LaboratoryUserBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1799")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "人员实体")
    private User user;

    @Schema(description = "实验室")
    private LaboratoryLab lab;
}

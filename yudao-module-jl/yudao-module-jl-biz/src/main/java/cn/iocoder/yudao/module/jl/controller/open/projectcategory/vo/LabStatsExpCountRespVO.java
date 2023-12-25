package cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo;

import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "实验室任务数量统计 Response VO")
@Data
@ToString(callSuper = true)
public class LabStatsExpCountRespVO {

    @Schema(description = "在做数量")
    private Integer doingCount;

    @Schema(description = "未做数量")
    private Integer notDoCount;

}

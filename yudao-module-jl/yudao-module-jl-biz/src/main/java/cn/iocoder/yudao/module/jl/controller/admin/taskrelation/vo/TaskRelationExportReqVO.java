package cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 任务关系依赖 Excel 导出 Request VO，参数和 TaskRelationPageReqVO 是一致的")
@Data
public class TaskRelationExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "任务id：category sop", example = "8795")
    private Long taskId;

    @Schema(description = "依赖id", example = "9680")
    private Long dependId;

}

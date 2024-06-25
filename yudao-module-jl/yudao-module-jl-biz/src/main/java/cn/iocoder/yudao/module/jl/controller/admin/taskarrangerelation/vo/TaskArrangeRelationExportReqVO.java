package cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 任务安排关系 Excel 导出 Request VO，参数和 TaskArrangeRelationPageReqVO 是一致的")
@Data
public class TaskArrangeRelationExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "任务id", example = "14875")
    private Long taskId;

    @Schema(description = "收费项id", example = "10287")
    private Long chargeItemId;

    @Schema(description = "产品id", example = "25050")
    private Long productId;

}

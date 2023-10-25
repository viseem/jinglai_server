package cn.iocoder.yudao.module.jl.controller.admin.reminder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 晶莱提醒事项 Excel 导出 Request VO，参数和 ReminderPageReqVO 是一致的")
@Data
public class ReminderExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "合同id", example = "26211")
    private Long contractId;

    @Schema(description = "项目id", example = "29941")
    private Long projectId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "截止日期")
    private String deadline;

    @Schema(description = "状态", example = "1")
    private String status;

}

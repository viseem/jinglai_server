package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "统计数据 - 基础请求 VO")
@Data
public class StatisticBaseVO {

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);

    @Schema(description = "结束日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime = LocalDateTime.now();

    @Schema(description = "人员id数组", example = "27395")
    private Long[] userIds;

    @Schema(description = "专题组id", example = "27395")
    private Long subjectGroupId;

    @Schema(description = "用户id", example = "27395")
    private Long userId;

    @Schema(description = "时间范围", example = "27395")
    private String timeRange = "month";
}

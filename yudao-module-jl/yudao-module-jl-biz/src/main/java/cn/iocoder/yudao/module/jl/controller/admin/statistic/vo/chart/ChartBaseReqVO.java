package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 文章分页 Request VO")
@Data
@ToString(callSuper = true)
public class ChartBaseReqVO {


    @Schema(description = "是否查询全部")
    private Boolean isAllAttribute = false;

    private LocalDateTime year;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    //今年年初的凌晨
    private LocalDateTime startTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0).withSecond(0);

    @Schema(description = "结束日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime = LocalDateTime.now();


}

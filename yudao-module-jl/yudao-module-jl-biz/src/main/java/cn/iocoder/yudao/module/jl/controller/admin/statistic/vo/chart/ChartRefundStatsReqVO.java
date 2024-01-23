package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 文章分页 Request VO")
@Data
@ToString(callSuper = true)
public class ChartRefundStatsReqVO extends ChartBaseReqVO {





}

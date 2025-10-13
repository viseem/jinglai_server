package cn.iocoder.yudao.module.jl.utils;

import cn.iocoder.yudao.module.jl.enums.TimeRangeTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 时间范围计算工具类
 */
public class TimeRangeUtil {

    @Data
    public static class TimeRange {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private TimeRangeTypeEnum type;

        public TimeRange(LocalDateTime startTime, LocalDateTime endTime, TimeRangeTypeEnum type) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.type = type;
        }
    }

    /**
     * 根据时间范围类型计算具体的时间范围
     */
    public static TimeRange calculateTimeRange(TimeRangeTypeEnum type) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start, end;

        switch (type) {
            case TODAY:
                start = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            case YESTERDAY:
                LocalDateTime yesterday = now.minusDays(1);
                start = yesterday.withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = yesterday.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            case THIS_WEEK:
                // 本周一00:00:00 到 当前时间
                int dayOfWeek = now.getDayOfWeek().getValue(); // 1=周一, 7=周日
                start = now.minusDays(dayOfWeek - 1).withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            case LAST_WEEK:
                // 上周一00:00:00 到 上周日23:59:59
                int lastWeekDayOfWeek = now.getDayOfWeek().getValue();
                LocalDateTime lastWeekStart = now.minusDays(lastWeekDayOfWeek + 6).withHour(0).withMinute(0).withSecond(0).withNano(0);
                start = lastWeekStart;
                end = lastWeekStart.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            case THIS_MONTH:
                // 本月1号00:00:00 到 当前时间
                start = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            case LAST_MONTH:
                // 上个月1号00:00:00 到 上个月最后一天23:59:59
                LocalDateTime lastMonth = now.minusMonths(1);
                start = lastMonth.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = lastMonth.withDayOfMonth(lastMonth.toLocalDate().lengthOfMonth())
                        .withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            case THIS_YEAR:
                // 本年1月1日00:00:00 到 当前时间
                start = now.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            case LAST_YEAR:
                // 去年1月1日00:00:00 到 去年12月31日23:59:59
                LocalDateTime lastYear = now.minusYears(1);
                start = lastYear.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = lastYear.withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;

            default:
                // 默认返回今天
                start = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;
        }

        return new TimeRange(start, end, type);
    }

    /**
     * 判断给定的时间范围是否匹配某个预设类型
     */
    public static TimeRangeTypeEnum matchTimeRangeType(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return TimeRangeTypeEnum.CUSTOM;
        }

        // 尝试匹配每种预设类型
        for (TimeRangeTypeEnum type : TimeRangeTypeEnum.values()) {
            if (type == TimeRangeTypeEnum.CUSTOM) {
                continue;
            }

            TimeRange calculatedRange = calculateTimeRange(type);
            
            // 比较时间（忽略纳秒差异）
            if (isSameTime(startTime, calculatedRange.getStartTime()) && 
                isSameTime(endTime, calculatedRange.getEndTime())) {
                return type;
            }
        }

        return TimeRangeTypeEnum.CUSTOM;
    }

    /**
     * 判断两个时间是否相同（精确到秒）
     */
    private static boolean isSameTime(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null || time2 == null) {
            return false;
        }
        return time1.truncatedTo(ChronoUnit.SECONDS).equals(time2.truncatedTo(ChronoUnit.SECONDS));
    }
}

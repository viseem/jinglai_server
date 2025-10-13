package cn.iocoder.yudao.module.jl.enums;

import lombok.Getter;

/**
 * 时间范围类型枚举
 */
@Getter
public enum TimeRangeTypeEnum {

    TODAY("TODAY", "今日"),
    YESTERDAY("YESTERDAY", "昨日"),
    THIS_WEEK("THIS_WEEK", "本周"),
    LAST_WEEK("LAST_WEEK", "上周"),
    THIS_MONTH("THIS_MONTH", "本月"),
    LAST_MONTH("LAST_MONTH", "上月"),
    THIS_YEAR("THIS_YEAR", "本年"),
    LAST_YEAR("LAST_YEAR", "上年"),
    CUSTOM("CUSTOM", "自定义");

    private final String code;
    private final String name;

    TimeRangeTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TimeRangeTypeEnum getByCode(String code) {
        for (TimeRangeTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return CUSTOM;
    }
}

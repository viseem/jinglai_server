package cn.iocoder.yudao.module.jl.enums;

import cn.iocoder.yudao.framework.common.core.StringArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付订单的状态枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum SubjectGroupMemberPostEnums implements StringArrayValuable {
    LEADER("1", "组长"),
    BUSINESS_LEADER("2", "商务组长"),
    SUB_LEADER("3", "副组长"),
    NO_POST("5", "无职务"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
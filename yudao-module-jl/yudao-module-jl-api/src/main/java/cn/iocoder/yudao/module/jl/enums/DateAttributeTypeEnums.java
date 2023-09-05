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
public enum DateAttributeTypeEnums implements StringArrayValuable {

    ALL("ALL", "全部"),
    SUB("SUB", "下属负责的"),
    MY("MY", "我负责的"),
    SEAS("SEAS", "公海"),
    ANY("ANY", "任何")
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
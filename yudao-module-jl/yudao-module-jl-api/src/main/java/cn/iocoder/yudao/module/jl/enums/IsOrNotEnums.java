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
public enum IsOrNotEnums implements StringArrayValuable {
    IS(1, "是"),
    NOT(0, "否"),
    ;

    private final Integer status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
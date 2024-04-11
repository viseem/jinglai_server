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
public enum ProcurementTypeEnums implements StringArrayValuable {
    PROJECT(1, "项目"),
    LAB(2, "实验室"),
    OFFICE(3, "行政"),
    ;

    private final Integer status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
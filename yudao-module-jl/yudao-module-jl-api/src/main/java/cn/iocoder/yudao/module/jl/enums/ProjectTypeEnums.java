package cn.iocoder.yudao.module.jl.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import cn.iocoder.yudao.framework.common.core.StringArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 支付订单的状态枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum ProjectTypeEnums implements StringArrayValuable {

    NormalProject("1", "常规项目"),
    EmergencyProject("2", "临时应急项目"),
    QuotationProject("3", "报价")

    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
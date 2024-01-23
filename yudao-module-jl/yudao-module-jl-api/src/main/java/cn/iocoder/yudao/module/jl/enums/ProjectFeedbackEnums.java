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
public enum ProjectFeedbackEnums implements StringArrayValuable {

    PROCESSED("1", "已处理"),
    NOT_PROCESS("2", "未处理"),

    INNER_TYPE("INNER","内部"),
    CUSTOMER_TYPE("CUSTOMER","客户")
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
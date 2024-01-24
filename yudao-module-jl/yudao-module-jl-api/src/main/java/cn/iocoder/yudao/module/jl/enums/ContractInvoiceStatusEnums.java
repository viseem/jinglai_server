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
public enum ContractInvoiceStatusEnums implements StringArrayValuable {
    NOT_INVOICE("1", "未开票"),
    INVOICED("2", "已开票"),

    RECEIVED_NO("0", "未收款"),
    RECEIVED_ALL("1", "已收款"),
    RECEIVED_PART("2", "部分收款"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
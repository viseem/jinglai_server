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

/*    NOT_INVOICE("1", "未开票"),
    INVOICED("2", "已开票"),

    RECEIVED_NO("0", "未到账"),
    RECEIVED_ALL("1", "已到账"),
    RECEIVED_PART("2", "部分收款"),
    ;*/
    NOT_INVOICE("1", "未开票"),
    //未到账 已到账 待退回 已退回  待作废 已作废
    RECEIVED_NO("10", "未到账"),
    WAIT_BACK("20", "待退回"),
    WAIT_VOID("30", "待作废"),
    BACKED("40", "已退回"),
    RECEIVED("50", "已到账"),
    VOIDED("60", "已作废"),
    ;

    public static String getStatusByText(String text) {
        if (text != null) {
            if (text.contains("未到账")) {
                return RECEIVED_NO.getStatus();
            }
            if (text.contains("已到账")) {
                return RECEIVED.getStatus();
            }
            if (text.contains("待退回")) {
                return WAIT_BACK.getStatus();
            }
            if (text.contains("已退回")) {
                return BACKED.getStatus();
            }
            if (text.contains("待作废")) {
                return WAIT_VOID.getStatus();
            }
            if (text.contains("已作废")) {
                return VOIDED.getStatus();
            }
        }
        return RECEIVED_NO.getStatus();
    }

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }


}
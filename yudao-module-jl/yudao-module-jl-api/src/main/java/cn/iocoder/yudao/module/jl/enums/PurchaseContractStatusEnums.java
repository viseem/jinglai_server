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
public enum PurchaseContractStatusEnums implements StringArrayValuable {
    //信息确认 财务审批 领导审批 出纳制单 打款凭证 已同意 已拒绝
    CONFIRM_INFO("1", "采购确认"),
    FINANCE_APPROVAL("2", "财务审批"),
    LEADER_APPROVAL("3", "领导审批"),
    CASHIER_MAKE("4", "出纳制单"),
    PAYMENT_VOUCHER("5", "打款凭证"),
    APPROVE("6", "已同意"),
    REJECT("7", "已拒绝")
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
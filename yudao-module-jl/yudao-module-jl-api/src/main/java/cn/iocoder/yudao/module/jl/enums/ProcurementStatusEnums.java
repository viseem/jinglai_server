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
public enum ProcurementStatusEnums implements StringArrayValuable {
    //信息确认 领导审批 购销合同 财务审批 领导二次审批 出纳制单 打款凭证 已签收
    CONFIRM_INFO("1", "信息确认"),
    LEADER_APPROVAL("2", "领导审批"),
    PURCHASE_SALES_CONTRACT("3", "购销合同"),
    FINANCE_APPROVAL("4", "财务审批"),
    LEADER_SECOND_APPROVAL("5", "领导二次审批"),
    CASHIER_MAKE_OUT("6", "出纳制单"),
    PAYMENT_VOUCHER("7", "打款凭证"),
    WAITING_CHECK_IN("8", "待签收"),
    WAITING_IN("9", "待入库"),
    IS_IN("100", "已签收"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
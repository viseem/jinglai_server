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
    FINANCE_APPROVAL("1", "财务审批"),
    LEADER_APPROVAL("2", "领导审批"),
    APPROVE("3", "已通过审批"),
    REJECT("4", "已拒绝")
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
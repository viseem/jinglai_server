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
    LAB_AUDIT("5", "实验室负责人审批"),
    CONFIRM_INFO("1", "采购确认"),
    LEADER_APPROVAL("2", "领导审批"),
    APPROVE("3", "已批准"),
    REJECT("4", "已拒绝"),
    CANCEL("100", "已取消"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
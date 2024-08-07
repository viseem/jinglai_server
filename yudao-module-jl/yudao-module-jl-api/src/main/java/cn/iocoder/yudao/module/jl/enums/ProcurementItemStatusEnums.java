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
public enum ProcurementItemStatusEnums implements StringArrayValuable {
    //申请采购 已批准采购 发起订购 已订购 已发货 部分入库 全部入库
    CANCEL("0", "取消采购"),
    APPLY_PROCUREMENT("1", "申请采购"),
    REJECT_PROCUREMENT("8", "拒绝采购"),
    APPROVE_PROCUREMENT("2", "已批准采购"),
    INITIATE_ORDER("3", "申请订购"),
    CANCEL_ORDER("9", "取消订购"),
    REJECT_ORDER("7", "拒绝订购"),
    ORDERED("4", "已订购"),
//    SHIPPED("5", "已发货"),
    PART_STORAGE("5", "部分入库"),
    ALL_STORAGE("6", "全部入库"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
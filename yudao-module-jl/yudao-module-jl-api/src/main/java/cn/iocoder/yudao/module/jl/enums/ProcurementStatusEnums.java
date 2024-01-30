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

    WAITING_CONFIRM_INFO("WAITING_CONFIRM_INFO", "提交信息"),
    WAITING_COMPANY_CONFIRM("WAITING_COMPANY_CONFIRM", "公司审核"),
    WAITING_FINANCE_CONFIRM("WAITING_FINANCE_CONFIRM", "财务审核"),
    WAITING_START_PROCUREMENT("WAITING_START_PROCUREMENT", "采购中"),
    WAITING_CHECK_IN("WAITING_CHECK_IN", "等待签收"),
    WAITING_IN("WAITING_IN", "等待入库"),
    IS_IN("IS_IN", "已入库"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
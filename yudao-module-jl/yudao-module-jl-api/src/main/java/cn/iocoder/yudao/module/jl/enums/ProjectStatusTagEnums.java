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
public enum ProjectStatusTagEnums implements StringArrayValuable {

    //预付款未到
    FIRST_FUND_NOT("1", "预付款未到"),
    //预付款已到
    FIRST_FUND_DONE("101", "预付款已到"),
    //推进
    DOING("3", "推进"),
    //暂停
    PAUSE("4", "暂停"),
    //中止
    STOP("5", "中止"),
    //退单
    BACK("6", "退单"),
    //提前出库
    OUT_EARLY("8", "提前出库"),
    //已完成待出库
    DONE_WAIT_OUT("11", "已完成待出库"),
    //已完成待收尾款
    DONE_WAIT_LAST_FUND("12", "已完成待收尾款"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
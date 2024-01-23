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
public enum ProjectFundEnums implements StringArrayValuable {
    BILL_PAID("2", "已开票已支付"),
    BILL_UNPAID("4", "已开票未支付"),
    UNBILL_PAID("1", "未开票已支付"),
    UNBILL_UNPAID("3", "未开票未支付"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
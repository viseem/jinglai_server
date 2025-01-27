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
public enum AnimalFeedStageEnums implements StringArrayValuable {
    DEFAULT("1", "饲养中"),
    FEEDING("FEEDING", "饲养中"),
    END("END", "饲养结束"),
    APPROVAL_FAIL("3", "拒绝"),
    APPROVAL_SUCCESS("2", "同意"),

    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
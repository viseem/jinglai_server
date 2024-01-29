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
public enum SubjectGroupMemberRoleEnums implements StringArrayValuable {
    LEADER("LEADER", "组长"),
    BUSINESS_LEADER("BUSINESS_LEADER", "商务组长"),
    SALE("SALE", "销售"),
    PROJECT("PROJECT", "项目"),
    CELL("CELL", "细胞成员"),
    ANIMAL("ANIMAL", "动物成员"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
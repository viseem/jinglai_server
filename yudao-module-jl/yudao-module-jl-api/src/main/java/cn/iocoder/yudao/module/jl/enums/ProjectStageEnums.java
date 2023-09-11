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
public enum ProjectStageEnums implements StringArrayValuable {

    DOING("3", "开展中"),
    DOING_PREVIEW("2", "开展前审批"),
    PRE("1","预开展"),
    PAUSE("4","暂停"),
    OUTED("9","出库"),
    OUTING("7","出库审批"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
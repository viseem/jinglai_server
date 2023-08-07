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
public enum ApprovalTypeEnums implements StringArrayValuable {

    PROJECT_STATUS_CHANGE("PROJECT_STATUS_CHANGE", "项目状态变更"),
    EXP_PROGRESS("EXP_PROGRESS", "实验审核推进"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
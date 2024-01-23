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
public enum CommonTodoEnums implements StringArrayValuable {

    DONE("DONE", "完成"),
    UN_DONE("UN_DONE", "未完成"),

    TYPE_PROJECT_CATEGORY("PROJECT_CATEGORY", "实验任务类型"),

    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
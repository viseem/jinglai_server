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
public enum AssetDeviceUseTypeEnums implements StringArrayValuable {

    PROJECT_USE("PROJECT_USE", "项目使用"),
    APPOINTMENT("APPOINTMENT", "预约使用")

    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
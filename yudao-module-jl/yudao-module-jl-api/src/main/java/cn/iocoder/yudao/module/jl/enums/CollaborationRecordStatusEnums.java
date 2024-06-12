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
public enum CollaborationRecordStatusEnums implements StringArrayValuable {
    SALESLEAD_SUGGEST("SALESLEAD-SUGGEST", "商机转化建议"),
    SALESLEAD("SALESLEAD", "商机沟通"),
    PROJECT("PROJECT", "项目沟通"),
    CONTRACT("CONTRACT", "合同沟通"),
    QUOTATION_COMMENT("QUOTATION_COMMENT", "报价评论"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }


}
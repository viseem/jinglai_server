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
public enum ProjectQuotationItemCreateTypeEnums implements StringArrayValuable {
    QUOTATION(0, "报价"),
    PROJECT(1, "项目中创建的"),
//    APPROVAL_SUCCESS("APPROVAL_SUCCESS","审批通过"),
//    APPROVAL_FAIL("APPROVAL_FAIL","审批不通过"),
    ;


    private final Integer status;
    private final String name;
//    public static final String[] DOING_ARRAY ={DOING.getStatus(),DATA_CHECK.getStatus(),PAUSE.getStatus(),DATA_ACCEPT.getStatus(),DATA_REJECT.getStatus()};

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

//    public static String[] getDoingStages(){
//        return new String[]{DOING.getStatus(),DATA_CHECK.getStatus(),PAUSE.getStatus(),DATA_ACCEPT.getStatus(),DATA_REJECT.getStatus()};
//    }

}
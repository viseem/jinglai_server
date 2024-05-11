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
public enum ProjectCategoryStatusEnums implements StringArrayValuable {
    WAIT_DO("E", "待开展"),
    DOING("DOING", "开展中"),
    DATA_CHECK("DATA_CHECK", "数据审核"),
    DATA_ACCEPT("DATA_ACCEPT", "数据审核通过"),
    DATA_REJECT("DATA_REJECT", "数据审核驳回"),
    PAUSE("DTAAA", "暂停"),
    DONE("V_DONE", "完成"),
    COMPLETE("Z_COMPLETE", "已出库"),


    APPROVAL_SUCCESS("APPROVAL_SUCCESS","审批通过"),
    APPROVAL_FAIL("APPROVAL_FAIL","审批不通过"),
    ;


    private final String status;
    private final String name;
    public static final String[] DOING_ARRAY ={DOING.getStatus(),DATA_CHECK.getStatus(),PAUSE.getStatus(),DATA_ACCEPT.getStatus(),DATA_REJECT.getStatus()};

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

    public static String[] getDoingStages(){
        return new String[]{DOING.getStatus(),DATA_CHECK.getStatus(),PAUSE.getStatus(),DATA_ACCEPT.getStatus(),DATA_REJECT.getStatus()};
    }

}
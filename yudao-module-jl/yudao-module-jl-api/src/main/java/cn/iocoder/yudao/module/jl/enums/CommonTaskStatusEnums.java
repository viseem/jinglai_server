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
public enum CommonTaskStatusEnums implements StringArrayValuable {
    DOING(10, "开展中"),
    PAUSE(20, "暂停"),
    DONE(30, "完成"),
    WAIT_DO(5, "待开展"),
/*    DATA_CHECK(55, "数据审核"),
    DATA_ACCEPT(50, "数据审核通过"),
    DATA_REJECT(45, "数据审核驳回"),
    COMPLETE(60, "已出库"),*/
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

    public static Integer[] getDoneStatus(){
        return new Integer[]{DONE.status};
    }

}
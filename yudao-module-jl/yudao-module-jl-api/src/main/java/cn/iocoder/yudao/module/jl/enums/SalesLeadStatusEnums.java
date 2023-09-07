package cn.iocoder.yudao.module.jl.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import cn.iocoder.yudao.framework.common.core.StringArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 支付订单的状态枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum SalesLeadStatusEnums implements StringArrayValuable {

    PotentialConsultation("1", "潜在咨询"),
    KeyFocus("2", "重点关注"),
    PendingDeal("3", "待成交"),
/*    CompletedTransaction("4", "已成交"),
    EmergencyProject("5", "临时应急项目"),*/
    LostDeal("4", "丢单"),
    ToProject("5","已转项目"),
    NotToProject("50","未转项目"),
    QUOTATION("6","报价中"),
    IS_QUOTATION("60","已报价"),
    ;

    private final String status;
    private final String name;

    @Override
    public List<String> array() {
        return new ArrayList<>();
    }

}
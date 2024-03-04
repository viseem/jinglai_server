package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales;

import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Schema(description = "销售端 分组统计数据")
@Data
public class SalesGroupStatisticResp {
    List<SalesGroupMember> salesGroupMemberList;
    List<SalesGroupMember> orderMemberList;
    List<SalesGroupMember> refundMemberList;
}

package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Schema(description = "销售端 跟进统计数据")
@Data
@ToString(callSuper = true)
public class SalesStatisticSalesleadResp {
    //总个数
    Integer salesleadCount;
    //重点关注个数
    Integer focusCount;
    //已报价个数
    Integer quotedCount;
    //已报价的成交个数
    Integer quotedAndDealCount;
    //已成交个数
    Integer dealCount;
    //已丢单个数
    Integer lostCount;


//    SalesStatisticSalesleadRespMap respMap;

}

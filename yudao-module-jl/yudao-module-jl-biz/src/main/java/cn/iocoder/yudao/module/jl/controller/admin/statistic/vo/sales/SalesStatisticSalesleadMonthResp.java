package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "销售端 跟进统计数据")
@Data
@ToString(callSuper = true)
public class SalesStatisticSalesleadMonthResp {
   List<SalesStatisticSalesleadResp> salesleadCountList;
}

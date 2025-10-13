package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.StatisticBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "销售数据统计请求参数")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesDataStatisticReqVO extends StatisticBaseVO {

}


package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.financial;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.StatisticBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "财务端（全部数据）统计的请求参数")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FinancialAllStatisticReqVO extends StatisticBaseVO {


}

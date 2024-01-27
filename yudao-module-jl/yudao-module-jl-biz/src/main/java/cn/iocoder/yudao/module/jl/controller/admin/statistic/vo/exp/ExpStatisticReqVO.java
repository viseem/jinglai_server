package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.exp;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.StatisticBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "实验端统计的请求参数")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExpStatisticReqVO extends StatisticBaseVO {


}

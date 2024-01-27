package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.sales;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.StatisticBaseVO;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.SubjectGroupBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "销售端统计的请求参数")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesStatisticReqVO extends StatisticBaseVO {


}

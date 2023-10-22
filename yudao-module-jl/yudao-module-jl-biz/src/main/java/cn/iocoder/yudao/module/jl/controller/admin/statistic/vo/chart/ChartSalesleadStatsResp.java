package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart;

import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFundOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class ChartSalesleadStatsResp {

    private List<Saleslead> list;

}

package cn.iocoder.yudao.module.jl.controller.admin.statistic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "")
@Data
@ToString(callSuper = true)
public class WorkstationSaleCountStatsResp {
    Integer salesLeadNotToProjectCount;

    //未收齐款项的数量
    Integer projectFundNotPayCompleteCount;

    Integer projectDoingCount;

    Integer projectNotCompleteCount;

    Integer not2CustomerCount;
}

package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Schema(description = "管理后台 - 项目管理 Response VO")
@Data
@ToString(callSuper = true)
public class ProjectCostStatsRespVO {
    @Schema(description = "合同应收")
    private Long contractAmount;

    @Schema(description = "合同已收")
    private Long contractReceivedAmount;


    @Schema(description = "物资成本")
    private Long supplyCost;

    @Schema(description = "采购成本")
    private Long procurementCost;

    @Schema(description = "收费项的成本")
    private Long chargeItemCost;

    @Schema(description = "委外的成本")
    private Long outsourceCost;

    @Schema(description = "报销的成本")
    private Long reimbursementCost;
}

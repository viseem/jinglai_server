package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 项目采购单物流信息 Response VO")
@Data
@ToString(callSuper = true)
public class SupplierStatsRespVO{

    @Schema(description = "打款合计")
    private Long fundAmount;

    @Schema(description = "开票合计")
    private Long invoiceAmount;

}

package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 更新物流信息 Request VO")
@Data
@ToString(callSuper = true)
public class ProcurementUpdateShipmentsReqVO {

    @Schema(description = "采购单号 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long procurementId;

    @Schema(description = "状态")
    private String status;

    private List<ProcurementShipmentCreateReqVO> shipments;

}

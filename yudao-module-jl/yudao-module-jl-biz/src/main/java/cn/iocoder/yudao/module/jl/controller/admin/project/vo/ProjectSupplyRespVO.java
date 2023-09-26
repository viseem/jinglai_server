package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.inventory.*;
import cn.iocoder.yudao.module.jl.entity.project.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 项目中的实验名目的物资项 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectSupplyRespVO extends ProjectSupplyBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31521")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
    @Schema(description = "采购信息")
    private List<ProcurementItem> procurements;

    @Schema(description = "寄来信息")
    private List<SupplySendInItem> sendIns;

    @Schema(description = "取货信息")
    private List<SupplyPickupItem> pickups;

    private ProjectSimple project;

    private Integer inedQuantity;
    private Integer outedQuantity;
    private Integer remainQuantity;

    //已申请------的采购数量
    private Integer procurementedQuantity;


    //入库记录
    private List<InventoryStoreIn> storeLogs;

    private InventoryStoreIn latestStoreLog;


    //出库记录
    private List<InventoryStoreOut> outLogs;
}

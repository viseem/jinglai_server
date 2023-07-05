package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
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
    private List<SupplyOutItem> supplyOutItems;

    private List<SupplyOutItem> supplyOutItems2;


    private ProjectOnly project;

}

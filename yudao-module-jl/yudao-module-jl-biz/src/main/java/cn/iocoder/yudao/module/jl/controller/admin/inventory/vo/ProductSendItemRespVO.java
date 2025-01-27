package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItem;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItemOnly;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 实验产品寄送明细 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductSendItemRespVO extends ProductSendItemBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25298")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private ProductInItemOnly productInItem;
}

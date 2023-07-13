package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductInOnly;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 实验产品入库明细 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductInItemRespVO extends ProductInItemBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15485")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private ProductInOnly productIn;

    private List<ProductSendItem> productSendItems;

    //入库的状态
    private String inStatus;
    //寄给客户的总数量
    private Integer sendedQuantity;
}

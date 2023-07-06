package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplyPickupItemCreateReqVO;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 实验产品寄送更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductSendSaveReqVO extends ProductSendBaseVO {

    @Schema(description = "ID")
    private Long id;

    private List<ProductSendItem> items;

}

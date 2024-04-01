package cn.iocoder.yudao.module.jl.controller.admin.productsop.vo;

import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品sop更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductSopUpdateReqVO extends ProductSopBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7713")
    @NotNull(message = "ID不能为空")
    private Long id;

    private List<ProductSop> list;
}

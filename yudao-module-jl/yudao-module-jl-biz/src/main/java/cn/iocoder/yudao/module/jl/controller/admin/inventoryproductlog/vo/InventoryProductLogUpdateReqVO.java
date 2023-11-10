package cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品变更日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InventoryProductLogUpdateReqVO extends InventoryProductLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4632")
    @NotNull(message = "ID不能为空")
    private Long id;

}

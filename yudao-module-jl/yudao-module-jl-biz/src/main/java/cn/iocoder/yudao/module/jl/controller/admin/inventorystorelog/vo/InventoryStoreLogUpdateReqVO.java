package cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 物品出入库日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InventoryStoreLogUpdateReqVO extends InventoryStoreLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10137")
    @NotNull(message = "ID不能为空")
    private Long id;

}

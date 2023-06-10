package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 库管存储容器位置更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InventoryContainerPlaceUpdateReqVO extends InventoryContainerPlaceBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19382")
    @NotNull(message = "ID不能为空")
    private Long id;

}

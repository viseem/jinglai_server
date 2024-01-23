package cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 收货仓库更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShipWarehouseUpdateReqVO extends ShipWarehouseBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7608")
    @NotNull(message = "ID不能为空")
    private Long id;

}

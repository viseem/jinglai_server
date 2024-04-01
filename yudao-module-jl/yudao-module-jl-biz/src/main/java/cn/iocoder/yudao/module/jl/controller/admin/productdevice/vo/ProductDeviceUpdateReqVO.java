package cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo;

import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品库设备更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDeviceUpdateReqVO extends ProductDeviceBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9099")
    @NotNull(message = "ID不能为空")
    private Long id;


    private List<ProductDevice> list;

}

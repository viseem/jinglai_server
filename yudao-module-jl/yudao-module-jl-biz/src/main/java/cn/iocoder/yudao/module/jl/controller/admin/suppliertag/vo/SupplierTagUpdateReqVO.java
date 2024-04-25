package cn.iocoder.yudao.module.jl.controller.admin.suppliertag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 供应商标签更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplierTagUpdateReqVO extends SupplierTagBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22606")
    @NotNull(message = "ID不能为空")
    private Long id;

}

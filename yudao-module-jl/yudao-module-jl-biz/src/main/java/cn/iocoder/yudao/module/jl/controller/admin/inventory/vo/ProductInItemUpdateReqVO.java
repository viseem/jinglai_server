package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 实验产品入库明细更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductInItemUpdateReqVO extends ProductInItemBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15485")
    @NotNull(message = "岗位ID不能为空")
    private Long id;

}

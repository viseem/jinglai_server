package cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 购销合同更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PurchaseContractUpdateReqVO extends PurchaseContractBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11518")
    @NotNull(message = "ID不能为空")
    private Long id;

}

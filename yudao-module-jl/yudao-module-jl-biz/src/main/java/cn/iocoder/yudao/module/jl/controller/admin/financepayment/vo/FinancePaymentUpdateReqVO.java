package cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 财务打款更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FinancePaymentUpdateReqVO extends FinancePaymentBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23213")
    @NotNull(message = "ID不能为空")
    private Long id;

}

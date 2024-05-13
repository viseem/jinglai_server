package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 开票申请更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvoiceApplicationUpdateReqVO extends InvoiceApplicationBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31650")
    @NotNull(message = "ID不能为空")
    private Long id;

}

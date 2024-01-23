package cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 采购供应商发票更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectSupplierInvoiceUpdateReqVO extends ProjectSupplierInvoiceBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3438")
    @NotNull(message = "ID不能为空")
    private Long id;

}

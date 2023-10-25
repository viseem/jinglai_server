package cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 合同发票记录更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractInvoiceLogUpdateReqVO extends ContractInvoiceLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24470")
    @NotNull(message = "ID不能为空")
    private Long id;

}

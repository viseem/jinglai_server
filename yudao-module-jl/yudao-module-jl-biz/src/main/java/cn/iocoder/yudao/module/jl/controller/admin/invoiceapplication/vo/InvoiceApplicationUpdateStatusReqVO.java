package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 开票申请更新 Request VO")
@Data
@ToString(callSuper = true)
public class InvoiceApplicationUpdateStatusReqVO  {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31650")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "状态", example = "22333", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private String status;


    @Schema(description = "审批意见", example = "赵六")
    private String auditMark;

}

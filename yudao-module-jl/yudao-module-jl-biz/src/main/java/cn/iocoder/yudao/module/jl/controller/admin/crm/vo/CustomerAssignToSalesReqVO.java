package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 客户更新 Request VO")
@Data
@ToString(callSuper = true)
public class CustomerAssignToSalesReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28406")
    @NotNull(message = "id不能为空")
    private Long id;


    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28406")
    @NotNull(message = "销售人员id不能为空")
    private Long salesId;

    private String mark;
}

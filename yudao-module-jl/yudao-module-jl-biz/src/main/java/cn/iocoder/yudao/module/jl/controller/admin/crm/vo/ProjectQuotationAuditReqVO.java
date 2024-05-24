package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 销售线索更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectQuotationAuditReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    @NotNull(message = "商机不能为空")
    private Long id;


}

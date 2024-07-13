package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 项目报价更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectQuotationUpdatePriceReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4075")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "报价金额")
    private BigDecimal originPrice;

    @Schema(description = "最终金额")
    private BigDecimal resultPrice;

}

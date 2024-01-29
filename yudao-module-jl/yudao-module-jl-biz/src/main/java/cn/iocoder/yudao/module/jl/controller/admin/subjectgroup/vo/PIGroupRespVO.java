package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 专题小组 KPI Response VO")
@Data
@ToString(callSuper = true)
public class PIGroupRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "594")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "专题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "专题不能为空")
    private String area;

    @Schema(description = "领域", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "领域不能为空")
    private String subject;

    private BigDecimal kpiOrderFund;
    private BigDecimal kpiOrderFundOf80;
    private BigDecimal kpiOrderFundOf120;

    private BigDecimal kpiReturnFund;
    private BigDecimal kpiReturnFundOf80;
    private BigDecimal kpiReturnFundOf120;
}

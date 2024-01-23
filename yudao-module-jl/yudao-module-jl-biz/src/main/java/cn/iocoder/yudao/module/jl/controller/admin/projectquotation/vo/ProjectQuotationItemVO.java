package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 项目报价 Response VO")
@Data
@ToString(callSuper = true)
public class ProjectQuotationItemVO {

    @Schema(description = "选中的实验名目 id")
    private Long projectCategoryId;

    private String projectCategoryName;
    private String projectCategoryCycle;

    private Long projectId;
    private Long quotationId;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "规则/单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规则/单位不能为空")
    private String spec;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单价不能为空")
    private Integer unitFee;

    @Schema(description = "成本价")
    private Integer buyPrice;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "备注")
    private String mark;

    private String brand;

    private String productCode;

    private String type;


    @Schema(description = "物品来源")
    private String source;
}

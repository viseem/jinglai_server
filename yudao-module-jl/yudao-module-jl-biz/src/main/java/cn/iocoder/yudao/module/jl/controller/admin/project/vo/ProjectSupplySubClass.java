package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 子项
 */
@Data
public class ProjectSupplySubClass {

    private Long id;

    @Schema(description = "实验名目 id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10361")
    private Long projectCategoryId;

    @Schema(description = "原始的实验名目 id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "18163")
    @NotNull(message = "原始的实验名目 id不能为空")
    private Long categoryId;

    private Long projectId;

    private Long scheduleId;

    @Schema(description = "物资 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15603")
    @NotNull(message = "物资 id不能为空")
    private Long supplyId;

    private String type;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "规则/单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规则/单位不能为空")
    private String feeStandard;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单价不能为空")
    private String unitFee;

    @Schema(description = "单量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单量不能为空")
    private Integer unitAmount;

    @Schema(description = "成本价")
    private Integer buyPrice;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "物品来源")
    private String source;

    @Schema(description = "采购总量")
    private Integer procurementQuantity;

    @Schema(description = "库存总量")
    private Integer inventoryQuantity;
}

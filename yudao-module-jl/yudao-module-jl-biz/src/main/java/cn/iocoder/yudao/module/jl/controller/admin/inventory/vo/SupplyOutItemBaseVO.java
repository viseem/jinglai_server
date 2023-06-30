package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 出库申请明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplyOutItemBaseVO {

    @Schema(description = "出库申请表 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "756")
    @NotNull(message = "出库申请表 id不能为空")
    private Long supplyOutId;

    @Schema(description = "物资 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30488")
    @NotNull(message = "物资 id不能为空")
    private Long projectSupplyId;

/*    @Schema(description = "物资库的物资 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8449")
    @NotNull(message = "物资库的物资 id不能为空")
    private Long supplyId;*/

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
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

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注")
    private String mark;

    private Integer outQuantity = 0;
}

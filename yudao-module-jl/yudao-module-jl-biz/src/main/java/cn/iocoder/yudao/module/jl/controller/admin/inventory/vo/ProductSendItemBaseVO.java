package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验产品寄送明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductSendItemBaseVO {

    @Schema(description = "实验产品寄送表 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21922")
    @NotNull(message = "实验产品寄送表 id不能为空")
    private Long productSendId;

    @Schema(description = "产品 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24301")
    @NotNull(message = "产品 id不能为空")
    private Long productId;

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

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "有效期不能为空")
    private String validDate;

    @Schema(description = "存储温度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "存储温度不能为空")
    private String temperature;

}

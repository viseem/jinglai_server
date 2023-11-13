package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 取货单申请明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplyPickupItemBaseVO {

    @Schema(description = "取货单申请id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5562")
    @NotNull(message = "取货单申请id不能为空")
    private Long supplyPickupId;

    @Schema(description = "项目物资 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1264")
    @NotNull(message = "项目物资 id不能为空")
    private Long projectSupplyId;

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

    private String brand;
    private String productCode;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "有效期不能为空")
    private String validDate;

    @Schema(description = "存储温度", requiredMode = Schema.RequiredMode.REQUIRED)
    private String temperature;

    @Schema(description = "状态", example = "2")
    private String status;


    private Long roomId;
    private Long placeId;
    private Long containerId;
    private String locationName;
    private Integer inQuantity = 0;
    private Integer checkInQuantity = 0;
}

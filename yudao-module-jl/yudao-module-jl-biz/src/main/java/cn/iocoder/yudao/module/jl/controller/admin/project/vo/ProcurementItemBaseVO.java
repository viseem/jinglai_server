package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目采购单申请明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProcurementItemBaseVO {

    @Schema(description = "采购单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26560")
    @NotNull(message = "采购单id不能为空")
    private Long procurementId;

    @Schema(description = "项目物资 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30207")
    @NotNull(message = "项目物资 id不能为空")
    private Long projectSupplyId;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "采购规则/单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采购规则/单位不能为空")
    private String feeStandard;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单价不能为空")
    private String unitFee;

    @Schema(description = "单量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单量不能为空")
    private Integer unitAmount = 0;

    @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采购数量不能为空")
    private Integer quantity = 0;



    @Schema(description = "供货商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6961")
    @NotNull(message = "供货商id不能为空")
    private Long supplierId;

    @Schema(description = "原价", requiredMode = Schema.RequiredMode.REQUIRED, example = "31202")
    @NotNull(message = "原价不能为空")
    private Integer buyPrice = 0;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "22107")
    @NotNull(message = "销售价不能为空")
    private Integer salePrice = 0;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "有效期", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime validDate;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "目录号")
    private String catalogNumber;

    @Schema(description = "货期")
    private String deliveryDate;

    @Schema(description = "状态:等待采购信息、等待打款、等待采购、等待签收、等待入库", example = "2")
    private String status;

    private Long roomId;
    private Long placeId;
    private Long containerId;
    private String temperature;
    private Integer inQuantity = 0;
    private Integer checkInQuantity = 0;
}

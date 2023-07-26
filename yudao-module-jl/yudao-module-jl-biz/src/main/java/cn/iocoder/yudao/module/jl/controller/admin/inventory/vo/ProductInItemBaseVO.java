package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验产品入库明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductInItemBaseVO {

    @Schema(description = "实验产品入库表 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17715")
    @NotNull(message = "实验产品入库表 id不能为空")
    private Long productInId;

    @Schema(description = "产自实验物资的 id", example = "15067")
    private Long sourceSupplyId;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
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

    @Schema(description = "房间 id", example = "20700")
    private Long roomId;

    @Schema(description = "储存器材 id", example = "30446")
    private Long containerId;

    @Schema(description = "区域位置 id", example = "11044")
    private Long zoomId;
    @Schema(description = "位置", example = "11044")
    private String locationName;

    @Schema(description = "有效期")
    private String validDate;

    @Schema(description = "存储温度")
    private String temperature;

    private Integer inQuantity = 0;

}

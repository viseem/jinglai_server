package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 物资寄来单申请明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SupplySendInItemBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11130")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "27443")
    private Long projectCategoryId;

    @Schema(description = "物资寄来申请表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22422")
    @NotNull(message = "物资寄来申请表id不能为空")
    private Long supplySendInId;

    @Schema(description = "项目物资 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20737")
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

    @Schema(description = "状态", example = "2")
    private String status;

}

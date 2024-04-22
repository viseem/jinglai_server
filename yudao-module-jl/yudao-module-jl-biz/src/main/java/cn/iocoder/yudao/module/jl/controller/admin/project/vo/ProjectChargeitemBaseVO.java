package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 项目中的实验名目的收费项 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectChargeitemBaseVO {

    @Schema(description = "选中的实验名目 id")
    private Long projectCategoryId;

    @Schema(description = "原始的实验名目 id")
    private Long categoryId;

    @Schema(description = "物资 id")
    private Long chargeItemId;

    private Integer finalUsageNum;

    private Integer isAppend;

    private Long projectId;
    private Long quotationId;
    private Long scheduleId;

    private Integer sort;
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "名称不能为空")
    private String name;


    @Schema(description = "规格")
    private String spec;

    @Schema(description = "规则/单位")
    private String feeStandard;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单价不能为空")
    private String unitFee;

    @Schema(description = "成本价")
    private Integer buyPrice;

    @Schema(description = "单量")
    private Integer unitAmount;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "折扣")
    private Integer discount;

    @Schema(description = "产品id")
    private Long productId;

    @Schema(description = "负责人id")
    private Long managerId;

}

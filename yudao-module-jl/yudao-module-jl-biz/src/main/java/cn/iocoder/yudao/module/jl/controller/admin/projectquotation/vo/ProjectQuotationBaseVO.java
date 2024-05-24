package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 项目报价 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectQuotationBaseVO {

    @Schema(description = "版本号")
    private String code;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "方案")
    private String planText;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19220")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "折扣")
    private Integer discount;

    @Schema(description = "实验材料折扣")
    private BigDecimal supplyDiscount;

    @Schema(description = "报价金额")
    private BigDecimal originPrice;

    @Schema(description = "最终金额")
    private BigDecimal resultPrice;

    @Schema(description = "减免金额")
    private BigDecimal reducePrice;

    @Schema(description = "实验名称", example = "李四")
    private String expName;

    @Schema(description = "实验目的")
    private String expPurpose;

    @Schema(description = "操作方法")
    private String operateMethod;

    @Schema(description = "注意事项")
    private String attention;

    @Schema(description = "风险提示")
    private String riskTips;

    @Schema(description = "交付标准")
    private String deliveryStandard;

    @Schema(description = "报价审批状态")
    private String auditStatus;

    @Schema(description = "报价审批备注")
    private String auditMark;

    @Schema(description = "报价审批id")
    private String auditProcessId;

    @Schema(description = "商机id")
    private Long salesleadId;
}

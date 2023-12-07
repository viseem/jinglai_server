package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 项目报价 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectQuotationNoRequireVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4075")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "版本号")
    private String code;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "方案")
    private String planText;

    @Schema(description = "项目id")
    private Long projectId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "折扣")
    private Integer discount;

}

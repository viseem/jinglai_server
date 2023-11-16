package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
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

}

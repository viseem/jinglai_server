package cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 报价变更日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class QuotationChangeLogBaseVO {

    @Schema(description = "项目id")
    private Long projectId;

    @Schema(description = "报价id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23423")
    @NotNull(message = "报价id不能为空")
    private Long quotationId;

    @Schema(description = "客户id")
    private Long customerId;

    @Schema(description = "变更原因", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更原因不能为空")
    private String mark;

    @Schema(description = "变更方案")
    private String planText;

    @Schema(description = "变更明细", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更明细不能为空")
    private String jsonLogs;

    private BigDecimal prevAmount=BigDecimal.ZERO;

    private BigDecimal currentAmount=BigDecimal.ZERO;

}

package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目采购单打款 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProcurementPaymentBaseVO {

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21105")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "采购单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21105")
    @NotNull(message = "采购单id不能为空")
    private Long procurementId;

    @Schema(description = "打款时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "打款时间不能为空")
    private String paymentDate;

    @Schema(description = "打款金额")
    private String amount;

    @Schema(description = "供货商 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14390")
    @NotNull(message = "供货商 id不能为空")
    private Long supplierId;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "凭证", example = "王五")
    private String proofName;

    @Schema(description = "凭证地址", example = "https://www.iocoder.cn")
    private String proofUrl;

}

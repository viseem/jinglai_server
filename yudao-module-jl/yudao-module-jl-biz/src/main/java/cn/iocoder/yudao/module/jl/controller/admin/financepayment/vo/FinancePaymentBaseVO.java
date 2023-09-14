package cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo;

import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 财务打款 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FinancePaymentBaseVO {

    @Schema(description = "申请单", requiredMode = Schema.RequiredMode.REQUIRED, example = "19904")
    @NotNull(message = "申请单不能为空")
    private Long refId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "审批人", example = "14780")
    private Long auditUserId;

    @Schema(description = "审批说明")
    private String auditMark;

    @Schema(description = "审批状态", example = "2")
    private String auditStatus = String.valueOf(BpmProcessInstanceResultEnum.PROCESS.getResult());

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "8057")
    @NotNull(message = "金额不能为空")
    private BigDecimal price;

    @Schema(description = "凭据", example = "https://www.iocoder.cn")
    private String proofUrl;

    @Schema(description = "凭据名称", example = "张三")
    private String proofName;

    @Schema(description = "打款凭证", example = "https://www.iocoder.cn")
    private String paymentUrl;

    @Schema(description = "打款凭证名称", example = "王五")
    private String paymentName;

}

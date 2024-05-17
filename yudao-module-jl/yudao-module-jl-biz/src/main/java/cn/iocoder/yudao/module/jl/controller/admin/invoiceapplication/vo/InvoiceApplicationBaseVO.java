package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 开票申请 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InvoiceApplicationBaseVO {

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31489")
    @NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "客户", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "客户不能为空")
    private String customerName;

    @Schema(description = "客户抬头id")
    private Long customerInvoiceHeadId;

    @Schema(description = "开票要求", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开票要求不能为空")
    private String require;

    @Schema(description = "抬头", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "抬头不能为空")
    private String head;

    @Schema(description = "税号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "税号不能为空")
    private String taxNumber;

    @Schema(description = "单位地址")
    private String address;

    @Schema(description = "寄送地址")
    private String sendAddress;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "开户银行", example = "李四")
    private String bankName;

    @Schema(description = "银行账号")
    private String bankAccount;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "发票数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10062")
    @NotNull(message = "发票数量不能为空")
    private Integer invoiceCount = 0;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "已开票金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "已开票金额不能为空")
    private BigDecimal invoicedAmount = BigDecimal.ZERO;

    @Schema(description = "已到账金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "已到账金额不能为空")
    private BigDecimal receivedAmount = BigDecimal.ZERO;

    @Schema(description = "已退回金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "已退回金额不能为空")
    private BigDecimal refundedAmount = BigDecimal.ZERO;

    @Schema(description = "销售id")
    private Long salesId;

    @Schema(description = "销售")
    private String salesName;

    @Schema(description = "审批人id", example = "22333")
    private Long auditId;

    @Schema(description = "审批人", example = "赵六")
    private String auditName;

    @Schema(description = "审批意见", example = "赵六")
    private String auditMark;

}

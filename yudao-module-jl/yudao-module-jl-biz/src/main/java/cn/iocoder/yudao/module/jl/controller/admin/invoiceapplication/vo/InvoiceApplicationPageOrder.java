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
 * 开票申请 Order 设置，用于分页使用
 */
@Data
public class InvoiceApplicationPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerName;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerInvoiceHeadId;

    @Schema(allowableValues = {"desc", "asc"})
    private String require;

    @Schema(allowableValues = {"desc", "asc"})
    private String head;

    @Schema(allowableValues = {"desc", "asc"})
    private String taxNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String address;

    @Schema(allowableValues = {"desc", "asc"})
    private String sendAddress;

    @Schema(allowableValues = {"desc", "asc"})
    private String phone;

    @Schema(allowableValues = {"desc", "asc"})
    private String bankName;

    @Schema(allowableValues = {"desc", "asc"})
    private String bankAccount;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String invoiceCount;

    @Schema(allowableValues = {"desc", "asc"})
    private String totalAmount;

    @Schema(allowableValues = {"desc", "asc"})
    private String invoicedAmount;

    @Schema(allowableValues = {"desc", "asc"})
    private String receivedAmount;

    @Schema(allowableValues = {"desc", "asc"})
    private String refundedAmount;

    @Schema(allowableValues = {"desc", "asc"})
    private String salesId;

    @Schema(allowableValues = {"desc", "asc"})
    private String salesName;

    @Schema(allowableValues = {"desc", "asc"})
    private String auditId;

    @Schema(allowableValues = {"desc", "asc"})
    private String auditName;

}

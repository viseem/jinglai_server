package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import cn.iocoder.yudao.module.jl.enums.DataAttributeTypeEnums;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 开票申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvoiceApplicationPageReqVO extends PageParam {

    @Schema(description = "归属：ALL MY SUB")
    private String attribute;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "客户id", example = "31489")
    private Long customerId;

    @Schema(description = "客户", example = "王五")
    private String customerName;

    @Schema(description = "客户抬头id", example = "29478")
    private Long customerInvoiceHeadId;

    @Schema(description = "开票要求")
    private String require;

    @Schema(description = "抬头")
    private String head;

    @Schema(description = "税号")
    private Integer taxNumber;

    @Schema(description = "单位地址")
    private String address;

    @Schema(description = "寄送地址")
    private String sendAddress;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "开户银行", example = "李四")
    private String bankName;

    @Schema(description = "银行账号", example = "27255")
    private String bankAccount;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "发票数量", example = "10062")
    private Integer invoiceCount;

    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    @Schema(description = "已开票金额")
    private BigDecimal invoicedAmount;

    @Schema(description = "已到账金额")
    private BigDecimal receivedAmount;

    @Schema(description = "已退回金额")
    private BigDecimal refundedAmount;

    @Schema(description = "销售id", example = "11004")
    private Long salesId;

    @Schema(description = "销售", example = "芋艿")
    private String salesName;

    @Schema(description = "审批人id", example = "22333")
    private Long auditId;

    @Schema(description = "审批人", example = "赵六")
    private String auditName;

}

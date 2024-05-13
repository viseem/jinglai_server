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

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 开票申请 Excel VO
 *
 * @author 惟象科技
 */
@Data
public class InvoiceApplicationExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("客户")
    private String customerName;

    @ExcelProperty("客户抬头id")
    private Long customerInvoiceHeadId;

    @ExcelProperty("开票要求")
    private String require;

    @ExcelProperty("抬头")
    private String head;

    @ExcelProperty("税号")
    private Integer taxNumber;

    @ExcelProperty("单位地址")
    private String address;

    @ExcelProperty("寄送地址")
    private String sendAddress;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("开户银行")
    private String bankName;

    @ExcelProperty("银行账号")
    private String bankAccount;

    @ExcelProperty("备注")
    private String mark;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("发票数量")
    private Integer invoiceCount;

    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    @ExcelProperty("已开票金额")
    private BigDecimal invoicedAmount;

    @ExcelProperty("已到账金额")
    private BigDecimal receivedAmount;

    @ExcelProperty("已退回金额")
    private BigDecimal refundedAmount;

    @ExcelProperty("销售id")
    private Long salesId;

    @ExcelProperty("销售")
    private String salesName;

    @ExcelProperty("审批人id")
    private Long auditId;

    @ExcelProperty("审批人")
    private String auditName;

}

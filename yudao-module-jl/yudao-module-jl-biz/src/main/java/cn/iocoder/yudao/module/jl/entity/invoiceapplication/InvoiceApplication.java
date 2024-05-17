package cn.iocoder.yudao.module.jl.entity.invoiceapplication;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * 开票申请 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InvoiceApplication")
@Table(name = "jl_invoice_application")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InvoiceApplication extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    /**
     * 客户
     */
    @Column(name = "customer_name", nullable = false )
    private String customerName;

    /**
     * 客户抬头id
     */
    @Column(name = "customer_invoice_head_id", nullable = false )
    private Long customerInvoiceHeadId;

    /**
     * 开票要求
     */
    @Column(name = "`require`", nullable = false )
    private String require;

    /**
     * 抬头
     */
    @Column(name = "head", nullable = false )
    private String head;

    /**
     * 税号
     */
    @Column(name = "tax_number", nullable = false )
    private String taxNumber;

    /**
     * 单位地址
     */
    @Column(name = "address", nullable = false )
    private String address;

    /**
     * 寄送地址
     */
    @Column(name = "send_address", nullable = false )
    private String sendAddress;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 开户银行
     */
    @Column(name = "bank_name", nullable = false )
    private String bankName;

    /**
     * 银行账号
     */
    @Column(name = "bank_account", nullable = false )
    private String bankAccount;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 发票数量
     */
    @Column(name = "invoice_count", nullable = false )
    private Integer invoiceCount;

    /**
     * 总金额
     */
    @Column(name = "total_amount", nullable = false )
    private BigDecimal totalAmount;

    /**
     * 已开票金额
     */
    @Column(name = "invoiced_amount", nullable = false )
    private BigDecimal invoicedAmount;

    /**
     * 已到账金额
     */
    @Column(name = "received_amount", nullable = false )
    private BigDecimal receivedAmount;

    /**
     * 已退回金额
     */
    @Column(name = "refunded_amount", nullable = false )
    private BigDecimal refundedAmount;

    /**
     * 销售id
     */
    @Column(name = "sales_id", nullable = false )
    private Long salesId;

    /**
     * 销售
     */
    @Column(name = "sales_name", nullable = false )
    private String salesName;

    /**
     * 审批人id
     */
    @Column(name = "audit_id")
    private Long auditId;

    /**
     * 审批人
     */
    @Column(name = "audit_name")
    private String auditName;

    /**
     * 审批意见
     */
    @Column(name = "audit_mark")
    private String auditMark;

}

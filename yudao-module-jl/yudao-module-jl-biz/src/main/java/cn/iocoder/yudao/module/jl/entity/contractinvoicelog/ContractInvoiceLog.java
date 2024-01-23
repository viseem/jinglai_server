package cn.iocoder.yudao.module.jl.entity.contractinvoicelog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 合同发票记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ContractInvoiceLog")
@Table(name = "jl_contract_invoice_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContractInvoiceLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 发票编号
     */
    @Column(name = "code", nullable = false )
    private String code;

    /**
     * 文件地址
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;


    /**
     * 文件名字
     */
    @Column(name = "file_name", nullable = false )
    private String fileName;


    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerOnly customer;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 合同id
     */
    @Column(name = "contract_id", nullable = false )
    private Long contractId;


    /*
     * 销售id
     * */
    @Column(name = "sales_id", nullable = false )
    private Long salesId;

    /**
     * 开票金额
     */
    @Column(name = "price", nullable = false )
    private BigDecimal price;

    /**
     * 开票日期
     */
    @Column(name = "date")
    private LocalDateTime date;

    /**
     * 开票类型：增值税专用发票
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 发票号码
     */
    @Column(name = "number")
    private String number;

    /**
     * 实际开票日期
     */
    @Column(name = "actual_date")
    private LocalDateTime actualDate;

    /**
     * 物流单号
     */
    @Column(name = "shipment_number")
    private String shipmentNumber;

    /**
     * 开发方式：电子、纸质
     */
    @Column(name = "way")
    private String way;

    /**
     * 发票抬头id
     */
    @Column(name = "receipt_head_id")
    private Long receiptHeadId;

    /**
     * 发票抬头
     */
    @Column(name = "title")
    private String title;

    /**
     * 纳税人识别号
     */
    @Column(name = "taxer_number")
    private String taxerNumber;

    /**
     * 开户行
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 开户账号
     */
    @Column(name = "bank_account")
    private String bankAccount;

    /**
     * 开票地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 抬头类型
     */
    @Column(name = "head_type")
    private String headType;

    /**
     * 邮寄联系人
     */
    @Column(name = "send_contact")
    private String sendContact;

    /**
     * 联系电话
     */
    @Column(name = "send_phone")
    private String sendPhone;

    /**
     * 详细地址
     */
    @Column(name = "send_address")
    private String sendAddress;

    /**
     * 邮寄省份
     */
    @Column(name = "send_province")
    private String sendProvince;

    /**
     * 邮寄市
     */
    @Column(name = "send_city")
    private String sendCity;

    /**
     * 邮寄区
     */
    @Column(name = "send_area")
    private String sendArea;

    /**
     * 负责人
     */
    @Column(name = "manager")
    private Long manager;

    /**
     * 实际已收
     */
    @Column(name = "received_price", nullable = false )
    private BigDecimal receivedPrice;

    /**
     * 状态
     */
    @Column(name = "status", nullable = false )
    private String status;


    /**
     * 状态
     */
    @Column(name = "price_status", nullable = false )
    private Integer priceStatus;

    /**
     * 状态修改的人
     */
    @Column(name = "audit_id")
    private Long auditId;

    /**
     * 附件地址
     */
    @Column(name = "attachment_url", nullable = false )
    private String attachmentUrl;

    /**
     * 文件名字
     */
    @Column(name = "attachment_name", nullable = false )
    private String attachmentName;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "contract_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectConstractOnly contract;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "audit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User auditor;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}

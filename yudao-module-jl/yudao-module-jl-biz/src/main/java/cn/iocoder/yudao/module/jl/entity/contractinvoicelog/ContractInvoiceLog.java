package cn.iocoder.yudao.module.jl.entity.contractinvoicelog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
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
     * 发票申请编号
     */
    @Column(name = "code", nullable = false )
    private String code;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

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

    /**
     * 开票金额
     */
    @Column(name = "price", nullable = false )
    private Long price;

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
    private Long receivedPrice;

    /**
     * 状态
     */
    @Column(name = "status", nullable = false )
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "contract_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectConstractOnly contract;

}
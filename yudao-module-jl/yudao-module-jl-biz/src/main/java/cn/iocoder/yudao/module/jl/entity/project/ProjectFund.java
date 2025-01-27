package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceipt;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 项目款项 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectFund")
@Table(name = "jl_project_fund")
@DynamicUpdate
public class ProjectFund extends BaseEntity {

    /**
     * 项目 id
     */
    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "contract_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectConstractOnly contract;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", insertable = false, updatable = false)
    private User user;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 资金额度
     */
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * 已收款项
     */
    private BigDecimal receivedPrice = BigDecimal.ZERO;

    @Transient
    private BigDecimal receiptPrice = BigDecimal.valueOf(0.00);

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * 客户 id
     */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

    /**
     * 支付状态(未支付，部分支付，完全支付)
     */
    @Column(name = "status")
    private String status;

    /**
     * 支付时间
     */
    @Column(name = "paid_time")
    private LocalDateTime paidTime;

    /**
     * 支付的截止时间
     */
    @Column(name = "deadline")
    private LocalDateTime deadline;

    /**
     * 支付凭证上传地址
     */
    @Column(name = "receipt_url")
    private String receiptUrl;

    /**
     * 支付凭证文件名称
     */
    @Column(name = "receipt_name")
    private String receiptName;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 付款方
     */
    @Column(name = "payer")
    private String payer;

    /**
     * 收款方
     */
    @Column(name = "payee")
    private String payee;

    /**
     * 收款备注
     */
    @Column(name = "pay_mark")
    private String payMark;


    /**
     * 实际到款日期
     */
    @Column(name = "actual_payment_time", nullable = false )
    private LocalDateTime actualPaymentTime;


    /**
     * 打款明细
     */
    @OneToMany(mappedBy = "projectFund", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy("createTime desc")
    private List<ProjectFundLog> items;

    @Transient
    private ProjectFundLog latestFundLog;


    /**
     * 查询款项列表
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "fund_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CrmReceipt> receipts = new ArrayList<>();

}

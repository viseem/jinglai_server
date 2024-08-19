package cn.iocoder.yudao.module.jl.entity.crm;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectQuote;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.*;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 销售线索 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Saleslead")
@Table(name = "jl_crm_saleslead")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Saleslead extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 销售线索来源
     *
     * 枚举 {@link TODO sales_lead_source 对应的类}
     */
    @Column(name = "source", nullable = false )
    private String source;


    /**
     * 合同 id
     */
    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    /**
     * 关键需求
     */
    @Column(name = "requirement")
    private String requirement;

    /**
     * 预算(元)
     */
    @Column(name = "budget")
    private Long budget;

    /**
     * 报价
     */
    @Column(name = "quotation")
    private BigDecimal quotation;

    @Column(name = "quotation_price")
    private Long quotationPrice;

    /**
     * 状态
     *
     * 枚举 {@link TODO sales_lead_status 对应的类}
     */
    @Column(name = "status", nullable = false )
    private Integer status;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    private String businessType;

    /**
     * 丢单的说明
     */
    @Column(name = "lost_note")
    private String lostNote;

    /**
     * 绑定的销售报价人员
     */
    @Column(name = "manager_id")
    private Long managerId;

    /**
     * 指派备注
     */
    @Column(name = "assign_mark")
    private String assignMark;


    /**
     * 商机变动日志
     */
    @Column(name = "json_log")
    private String jsonLog;

    /**
     * 报价备注
     */
    @Column(name = "quotation_mark")
    private String quotationMark;

    /**
     * 报价附件
     */
    @Column(name = "quotation_json_file")
    private String quotationJsonFile;

    /*
     * 最近跟进时间
     * */
    @Column(name = "last_follow_time")
    private LocalDateTime lastFollowTime;


    /**
     * 报价的创建时间
     */
    @Column(name = "quotation_create_time")
    private LocalDateTime quotationCreateTime;

    /**
     * 报价最近更新时间
     */
    @Column(name = "quotation_update_time")
    private LocalDateTime quotationUpdateTime;

    @Column(name = "last_followup_id")
    private Long lastFollowUpId;

    /**
     * 报价审批状态
     */
    @Column(name = "quotation_audit_status",nullable = true)
    private String quotationAuditStatus;

    /**
     * 报价审批备注
     */
    @Column(name = "quotation_audit_mark",nullable = true)
    private String quotationAuditMark;

    /**
     * 报价审批id
     */
    @Column(name = "quotation_process_id",nullable = true)
    private String quotationProcessId;

    /**
     * 当前报价id
     */
    @Column(name = "current_quotation_id")
    private Long currentQuotationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User manager;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "last_followup_id", referencedColumnName="id", insertable = false, updatable = false)
    private Followup lastFollowup;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName="id", insertable = false, updatable = false)
    private ProjectSimple project;


    // 客户姓名
    @Transient
    private String customerName;

    @Transient
    private String institutionName;

    @Transient
    private String managerName;

    @Transient
    private String isQuotation;

    // 销售姓名
    @Transient
    private String salesName;

    // 最近跟进内容
    @Transient
    private String lastFollowContent;

}

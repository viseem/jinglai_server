package cn.iocoder.yudao.module.jl.entity.crm;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectQuote;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 销售线索 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SalesleadOnly")
@Table(name = "jl_crm_saleslead")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SalesleadOnly extends BaseEntity {

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



    @Column(name = "last_followup_id")
    private Long lastFollowUpId;


    // 客户姓名
    @Transient
    private String customerName;

    // 销售姓名
    @Transient
    private String salesName;

    // 最近跟进内容
    @Transient
    private String lastFollowContent;

}
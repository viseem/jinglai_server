package cn.iocoder.yudao.module.jl.entity.financepayment;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
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

/**
 * 财务打款 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "FinancePayment")
@Table(name = "jl_finance_payment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FinancePayment extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 申请单
     */
    @Column(name = "ref_id", nullable = false )
    private Long refId;

    /**
     * 申请单
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 审批人
     */
    @Column(name = "audit_user_id")
    private Long auditUserId;

    /**
     * 审批说明
     */
    @Column(name = "audit_mark")
    private String auditMark;

    /**
     * 审批状态
     */
    @Column(name = "audit_status")
    private String auditStatus;

    /**
     * 金额
     */
    @Column(name = "price", nullable = false )
    private BigDecimal price;

    /**
     * 凭据
     */
    @Column(name = "proof_url")
    private String proofUrl;

    /**
     * 凭据名称
     */
    @Column(name = "proof_name")
    private String proofName;

    /**
     * 打款凭证
     */
    @Column(name = "payment_url")
    private String paymentUrl;

    /**
     * 打款凭证名称
     */
    @Column(name = "payment_name")
    private String paymentName;

    /**
     * 流程实例id
     */
    @Column(name = "process_instance_id")
    private String processInstanceId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "audit_user_id", insertable = false, updatable = false)
    private User auditUser;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

}

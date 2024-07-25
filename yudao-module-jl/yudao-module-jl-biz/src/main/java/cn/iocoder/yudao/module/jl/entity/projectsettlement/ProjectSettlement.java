package cn.iocoder.yudao.module.jl.entity.projectsettlement;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
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
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 项目结算 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectSettlement")
@Table(name = "jl_project_settlement")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectSettlement extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 报价id
     */
    @Column(name = "quotation_id", nullable = false )
    private Long quotationId;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    /**
     * 结算金额
     */
    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    /**
     * 提醒日期
     */
    @Column(name = "reminder_date")
    private LocalDateTime reminderDate;

    /**
     * 结算日期
     */
    @Column(name = "paid_date")
    private LocalDateTime paidDate;

    /**
     * 应收金额
     */
    @Column(name = "amount", nullable = false )
    private BigDecimal amount;

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
     * 变更明细
     */
    @Column(name = "json_logs", nullable = false )
    private String jsonLogs;

    /**
     * 变更明细
     */
    @Column(name = "prev_amount", nullable = false )
    private BigDecimal prevAmount;

    /**
     * 结算单金额
     */
    @Column(name = "current_amount", nullable = false )
    private BigDecimal currentAmount;

    /**
     * 报价金额
     */
    @Column(name = "quotation_amount", nullable = false )
    private BigDecimal quotationAmount;

    /**
     * 合同默认结算金额
     */
    @Column(name = "contract_amount", nullable = false )
    private BigDecimal contractAmount;

    /**
     * 合同已收金额
     */
    @Column(name = "contract_received_amount", nullable = false )
    private BigDecimal contractReceivedAmount;

    /**
     * 级联
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /*
     * 级联附件
     * */
    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "type = 'PROJECT_SETTLEMENT'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CommonAttachment> attachmentList = new ArrayList<>();

}

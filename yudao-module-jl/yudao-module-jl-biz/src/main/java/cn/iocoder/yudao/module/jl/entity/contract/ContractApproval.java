package cn.iocoder.yudao.module.jl.entity.contract;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
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

/**
 * 合同状态变更记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ContractApproval")
@Table(name = "jl_contract_approval")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContractApproval extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 申请变更的状态
     */
    @Column(name = "stage", nullable = false )
    private String stage;

    /**
     * 申请的备注
     */
    @Column(name = "stage_mark", nullable = false )
    private String stageMark;

    /**
     * 审批备注
     */
    @Column(name = "approval_mark")
    private String approvalMark;

    /**
     * 审批状态：等待审批、批准、拒绝
     */
    @Column(name = "approval_stage")
    private String approvalStage;

    /**
     * 合同id
     */
    @Column(name = "contract_id", nullable = false )
    private Long contractId;

    /**
     * 变更前状态
     */
    @Column(name = "origin_stage", nullable = false )
    private String originStage;

    /**
     * 流程实例的编号
     */
    @Column(name = "process_instance_id")
    private String processInstanceId;

    /**
     * 合同结算金额
     */
    @Column(name = "real_price")
    private Integer realPrice;

    /**
     * 合同变更前结算金额
     */
    @Column(name = "origin_real_price")
    private Integer originRealPrice;


    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "contract_id",  insertable = false, updatable = false)
    private ProjectConstractOnly contract;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", insertable = false, updatable = false)
    private User user;

}

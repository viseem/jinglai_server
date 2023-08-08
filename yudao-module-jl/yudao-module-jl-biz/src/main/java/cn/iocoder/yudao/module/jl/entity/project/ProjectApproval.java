package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import cn.iocoder.yudao.module.jl.entity.approval.ApprovalOnly;
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
 * 项目的状态变更记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectApproval")
@Table(name = "jl_project_approval")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectApproval extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 申请变更的状态：提前开展、项目开展、暂停、中止、退单、提前出库、出库、售后
     */
    @Column(name = "stage", nullable = false )
    private String stage;

    /**
     * 变更前状态
     */
    @Column(name = "origin_stage", nullable = false )
    private String originStage;

    /**
     * 申请的备注
     */
    @Column(name = "stage_mark")
    private String stageMark;

    /**
     * 审批人id
     */
    @Column(name = "approval_user_id")
    private Long approvalUserId;

    /**
     * 审批备注
     */
    @Column(name = "approval_mark")
    private String approvalMark;

    /**
     * 审批状态：等待审批、批准、拒绝
     */
    @Column(name = "approval_stage", nullable = false )
    private String approvalStage;

    /**
     * 流程实例id
     */
    @Column(name = "process_instance_id", nullable = false )
    private String processInstanceId;

    /**
     * 项目的id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 安排单id
     */
    @Column(name = "schedule_id", nullable = false )
    private Long scheduleId;

    /*
    * 审批表的id
    * */

    @Column(name = "approval_id", nullable = false )
    private Long approvalId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "approval_id",  insertable = false, updatable = false)
    private Approval approval;

    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectOnly project;

/*    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "approval_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User approvalUser;*/

}

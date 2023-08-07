package cn.iocoder.yudao.module.jl.entity.approval;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
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
 * 审批流程 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ApprovalProgress")
@Table(name = "jl_approval_progress")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ApprovalProgress extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 审批id
     */
    @Column(name = "approval_id", nullable = false )
    private Long approvalId;

    /**
     * 用户id
     */
    @Column(name = "to_user_id", nullable = false )
    private Long toUserId;

    /**
     * 审批状态
     */
    @Column(name = "approval_stage")
    private String approvalStage;

    /**
     * 审批备注
     */
    @Column(name = "approval_mark")
    private String approvalMark;

    /**
     * 审批类型：抄送，审批
     */
    @Column(name = "type", nullable = false )
    private String type;

    /*
    * 是否最后一个
    * */
    private Boolean isLast;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "to_user_id", insertable = false, updatable = false)
    private User toUser;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "approval_id",  insertable = false, updatable = false)
    private ApprovalOnly approval;
}

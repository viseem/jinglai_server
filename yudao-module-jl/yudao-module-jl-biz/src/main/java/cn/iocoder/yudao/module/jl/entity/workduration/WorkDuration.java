package cn.iocoder.yudao.module.jl.entity.workduration;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 工时 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "WorkDuration")
@Table(name = "jl_work_duration")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorkDuration extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 时长(分)
     */
    @Column(name = "duration", nullable = false )
    private Integer duration;

    /**
     * 项目
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 实验名目
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 审批状态
     */
    @Column(name = "audit_status")
    private String auditStatus;

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


    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id",  insertable = false, updatable = false)
    private ProjectSimple project;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_category_id",  insertable = false, updatable = false)
    private ProjectCategoryOnly projectCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator",  insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "audit_user_id",insertable = false, updatable = false)
    private User auditUser;

}

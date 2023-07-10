package cn.iocoder.yudao.module.jl.entity.projectcategory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * 项目实验名目的状态变更审批 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectCategoryApproval")
@Table(name = "jl_project_category_approval")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectCategoryApproval extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 申请变更的状态：开始、暂停、数据审批
     */
    @Column(name = "stage", nullable = false)
    private String stage;

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
     * 审批状态：等待审批、已审批
     */
    @Column(name = "approval_stage")
    private String approvalStage;

    /**
     * 项目的实验名目id
     */
    @Column(name = "project_category_id", nullable = false)
    private Long projectCategoryId;

    @ManyToOne
    @JoinColumn(name="project_category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProjectCategory category;

    /**
     * 安排单id
     */
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;


    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}

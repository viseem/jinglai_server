package cn.iocoder.yudao.module.jl.entity.projectcategory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 项目实验名目的操作日志 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectCategoryLog")
@Table(name = "jl_project_category_log")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectCategoryLog extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * JPA 级联出 project
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProjectSimple project;

    /**
     * 实验名目 id
     */
    @Column(name = "project_category_id", nullable = false)
    private Long projectCategoryId;

/*    @ManyToOne
    @JoinColumn(name="project_category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProjectCategory category;*/

    /**
     * 原实验名目 id
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * 实验人员
     */
    @Column(name = "operator_id", nullable = false)
    private Long operatorId;

    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "operator_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User operator;

    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User user;

    /**
     * 备注
     */
    @Column(name = "mark", nullable = false)
    private String mark;

    /**
     * 评分
     */
    @Column(name = "score", nullable = false)
    private String score;

    /**
     * 操作时间
     */
    @Column(name = "operate_time", nullable = false)
    private LocalDateTime operateTime;

    /**
     * 查询附件列表
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_category_log_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectCategoryAttachment> attachments = new ArrayList<>();


    /*
    * 级联实验
    * */
    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectCategoryOnly projectCategory;

}

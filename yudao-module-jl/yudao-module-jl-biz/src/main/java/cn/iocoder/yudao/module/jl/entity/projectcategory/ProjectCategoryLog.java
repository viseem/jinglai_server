package cn.iocoder.yudao.module.jl.entity.projectcategory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
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
 * 项目实验名目的操作日志 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectCategoryLog")
@Table(name = "jl_project_category_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    private ProjectOnly project;

    /**
     * 实验名目 id
     */
    @Column(name = "project_category_id", nullable = false)
    private Long projectCategoryId;

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
    private User operator;

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

}

package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目中的实验名目的操作SOP Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectSop")
@Table(name = "jl_project_sop")
@SQLDelete(sql = "UPDATE jl_project_sop SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class ProjectSop extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 实验名目 id
     */
    @Column(name = "project_category_id", nullable = false )
    private Long projectCategoryId;

    /**
     * 原始实验名目 id
     */
    @Column(name = "category_id", nullable = false )
    private Long categoryId;

    /**
     * 操作步骤的内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 步骤序号
     */
    @Column(name = "step", nullable = false )
    private Integer step;

    /**
     * 注意事项
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 依赖项(json数组多个)
     */
    @Column(name = "depend_ids")
    private String dependIds;

    /**
     * 注意事项
     */
    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectCategoryOnly category;
}

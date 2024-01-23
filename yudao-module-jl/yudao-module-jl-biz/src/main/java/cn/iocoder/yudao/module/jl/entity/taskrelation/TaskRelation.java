package cn.iocoder.yudao.module.jl.entity.taskrelation;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSop;
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

/**
 * 任务关系依赖 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TaskRelationOnly")
@Table(name = "jl_task_relation")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TaskRelation extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 等级
     */
    @Column(name = "level", nullable = false )
    private Integer level;

    /**
     * 任务id：category sop
     */
    @Column(name = "task_id", nullable = false )
    private Long taskId;

    /**
     * 依赖id
     */
    @Column(name = "depend_id", nullable = false )
    private Long dependId;



    /**
     * 依赖等级
     */
    @Column(name = "depend_level", nullable = false )
    private Integer dependLevel;


    /*
    * 级联category
    * */
    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "depend_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @Where(clause = "depend_level = 2")
    private ProjectCategoryOnly category;


    /*
     * 级联sop
     * */
    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "depend_id",insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @Where(clause = "depend_level = 3")
    private ProjectSop sop;

}

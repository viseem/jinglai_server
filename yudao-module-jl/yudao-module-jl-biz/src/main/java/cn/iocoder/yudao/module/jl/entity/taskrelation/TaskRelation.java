package cn.iocoder.yudao.module.jl.entity.taskrelation;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Entity(name = "TaskRelation")
@Table(name = "jl_task_relation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
}

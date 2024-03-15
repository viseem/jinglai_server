package cn.iocoder.yudao.module.jl.entity.worktodotagrelation;

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
 * 工作任务 TODO 与标签的关联 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "WorkTodoTagRelation")
@Table(name = "jl_work_todo_tag_relation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorkTodoTagRelation extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * todo id
     */
    @Column(name = "todo_id", nullable = false )
    private Long todoId;

    /**
     * tag id
     */
    @Column(name = "tag_id", nullable = false )
    private Long tagId;

}

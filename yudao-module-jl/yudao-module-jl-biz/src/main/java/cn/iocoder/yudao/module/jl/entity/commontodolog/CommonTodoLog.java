package cn.iocoder.yudao.module.jl.entity.commontodolog;

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
 * 通用TODO记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CommonTodoLog")
@Table(name = "jl_common_todo_log")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommonTodoLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 实验任务id
     */
    @Column(name = "ref_id", nullable = false )
    private Long refId;

    /**
     * todoId
     */
    @Column(name = "todo_id", nullable = false )
    private Long todoId;

    /**
     * todo类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * todo状态
     */
    @Column(name = "status", nullable = false )
    private String status;

}

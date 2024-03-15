package cn.iocoder.yudao.module.jl.entity.worktodo;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 工作任务 TODO Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "WorkTodo")
@Table(name = "jl_work_todo")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorkTodo extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title", nullable = false )
    private String title;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 重要程度：重要紧急 不重要不紧急 重要不紧急
     *
     * 枚举 {@link TODO todo_priority 对应的类}
     */
    @Column(name = "priority")
    private String priority;

    /**
     * 期望截止日期（排期）
     */
    @Column(name = "deadline")
    private LocalDateTime deadline;

    /**
     * 状态：未受理、已处理
     */
    @Column(name = "status")
    private String status;

    /**
     * 类型(系统生成的任务、用户自己创建)
     */
    @Column(name = "type")
    private String type;

    /**
     * 引用的 id
     */
    @Column(name = "ref_id")
    private Long refId;

    /**
     * 引用的提示语句
     */
    @Column(name = "ref_desc")
    private String refDesc;

    /**
     * 负责人 id
     */
    @Column(name = "user_id")
    private Integer userId;

}

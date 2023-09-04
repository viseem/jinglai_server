package cn.iocoder.yudao.module.jl.entity.feedback;

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
 * 晶莱反馈 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Feedback")
@Table(name = "jl_feedback")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Feedback extends BaseEntity {

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
     * 重要程度：重要紧急 不重要不紧急 重要不紧急
     */
    @Column(name = "importance", nullable = false )
    private String importance;

    /**
     * 期望截止日期（排期）
     */
    @Column(name = "deadline")
    private String deadline;

    /**
     * 状态：未受理、处理中、已解决
     */
    @Column(name = "status", nullable = false )
    private String status;

    /**
     * 截图地址
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;

}

package cn.iocoder.yudao.module.jl.entity.projectfeedback;

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
 * 晶莱项目反馈关注人员 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectFeedbackFocus")
@Table(name = "jl_project_feedback_focus")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectFeedbackFocus extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false )
    private Long userId;

    /**
     * 项目反馈id
     */
    @Column(name = "project_feedback_id", nullable = false )
    private Long projectFeedbackId;

}

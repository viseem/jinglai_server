package cn.iocoder.yudao.module.jl.entity.projectpushlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
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
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * 项目推进记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectPushLog")
@Table(name = "jl_project_push_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectPushLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 推进内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 推进时间
     */
    @Column(name = "record_time")
    private LocalDateTime recordTime;

    /**
     * 阶段
     */
    @Column(name = "stage")
    private String stage;

    /**
     * 进度
     */
    @Column(name = "progress", nullable = false )
    private BigDecimal progress;

    /**
     * 预期进度
     */
    @Column(name = "expected_progress")
    private BigDecimal expectedProgress;

    /**
     * 风险
     */
    @Column(name = "risk")
    private String risk;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /*
     * 级联附件
     * */
    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "type = 'PROJECT_PUSH_LOG'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CommonAttachment> attachmentList = new ArrayList<>();

}

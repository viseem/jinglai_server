package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目安排单 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectSchedule")
@Table(name = "jl_project_schedule")
public class ProjectSchedule extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目 id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 报价单的名字
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 状态, 待审批、已审批
     */
    @Column(name = "status", nullable = false )
    private String status;

    /**
     * 方案状态
     */
    @Column(name = "plan_text", nullable = false )
    private String planText;
    @Column(name = "current_plan_id", nullable = false )
    private Long currentPlanId;

    /**
     * 实验名目
     */
/*    @OneToMany(mappedBy="schedule")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonManagedReference
    private List<ProjectCategory> categoryList;*/

    /**
     * 查询款项列表
     */
/*    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Where(clause="type = 'schedule'")
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectCategory> categoryList;*/

}

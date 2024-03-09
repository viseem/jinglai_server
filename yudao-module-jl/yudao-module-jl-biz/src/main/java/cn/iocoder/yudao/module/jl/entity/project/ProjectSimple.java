package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * 项目管理 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectSimple")
@Table(name = "jl_project_base")
public class ProjectSimple extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    /**
     * 课题组id
     */
    @Column(name = "subject_group_id")
    private Long subjectGroupId;

    /**
     * 销售线索 id
     */
    @Column(name = "saleslead_id")
    private Long salesleadId;


    /**
     * 项目名字
     */
    @Column(name = "name", nullable = false)
    private String name;

    private String code;

    /**
     * 项目开展阶段
     */
    @Column(name = "stage")
    private String stage;

    /**
     * 项目状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 项目类型
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 启动时间
     */
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * 截止时间
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /*
     * 最近跟进时间
     * */
    @Column(name = "last_follow_time")
    private LocalDateTime lastFollowTime;

    /**
     * 项目负责人
     */
    @Column(name = "manager_id")
    private Long managerId;

    /**
     * JPA 级联出 项目负责人
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User manager;

    /**
     * 原设计 参与者 ids，数组
     */
    @Column(name = "participants")
    private String participants;

    /**
     * 参与者 ids，数组
     */
    @Column(name = "focus_ids")
    private String focusIds;

    /**
     * tag ids
     */
    @Column(name = "tag_ids")
    private String tagIds;

    /**
     * 转移日志
     */
    @Column(name = "transfer_log")
    private String transferLog;

    /**
     * 销售 id
     */
    @Column(name = "sales_id")
    private Long salesId;

    /**
     * JPA 级联出 销售
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "sales_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User sales;

    /**
     * 客户 id
     */
    @Column(name = "customer_id")
    private Long customerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private CustomerOnly customer;
    /**
     * 当前安排单 id
     */
    @Column(name = "current_quotation_id")
    private Long currentQuotationId;

    /**
     * 当前安排单 id
     */
    @Column(name = "current_schedule_id")
    private Long currentScheduleId;

/*    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "saleslead_id",  insertable = false, updatable = false)
    private Saleslead saleslead;*/

    /**
     * 查询合同列表
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectConstractOnly> contracts = new ArrayList<>();

    @Transient
    private Integer completePercent;
    @Transient
    private Integer completeCount;
    @Transient
    private Integer allCount;
}

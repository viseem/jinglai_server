package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.crmsubjectgroup.CrmSubjectGroup;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目管理 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Project")
@Table(name = "jl_project_base")
public class Project extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 课题组id
     */
    @Column(name = "subject_group_id")
    private Long subjectGroupId;


    @Column(name = "process_instance_id", nullable = false )
    private String processInstanceId;

    /*
     * 项目出库申请结果
     * */

    @Column(name = "outbound_apply_result", nullable = false )
    private String outboundApplyResult;

    @Column(name = "outbound_user_id", nullable = false )
    private Long outboundUserId;


    /**
     * 项目编号
     */
    @Column(name = "code", nullable = false )
    private String code;

    /**
     * 销售线索 id
     */
    @Column(name = "saleslead_id")
    private Long salesleadId;

    /**
     * 项目名字
     */
    @Column(name = "name", nullable = false )
    private String name;

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
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 启动时间
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 截止时间
     */
    @Column(name = "end_date")
    private String endDate;

    /**
     * 项目负责人
     */
    @Column(name = "manager_id")
    private Long managerId;

    /**
     * 项目售前负责人
     */
    @Column(name = "pre_manager_id")
    private Long preManagerId;


    /**
     * 参与者 ids，数组
     */
    @Column(name = "focus_ids")
    private String focusIds;

    /**
     * 原设计 参与者 ids，数组
     */
    @Column(name = "participants")
    private String participants;
    /**
     * 销售 id
     */
    @Column(name = "sales_id")
    private Long salesId;

    /**
     * JPA 级联出 sales
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "sales_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User sales;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User manager;

    /**
     * 客户 id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 级联出客户信息
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerOnly customer;


    /**
     * 当前安排单 id
     */
    @Column(name = "current_schedule_id")
    private Long currentScheduleId;

    /**
     * 当前安排单 id
     */
    @Column(name = "current_quotation_id",insertable =false, updatable = false)
    private Long currentQuotationId;

    /**
     * 当前安排单
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_schedule_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ProjectSchedule currentSchedule;


    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;


    /**
     * 查询款项列表
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectConstractOnly> contractList = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "subject_group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CrmSubjectGroup subjectGroup;

    @Transient
    private Integer completePercent;
    @Transient
    private Long completeCount;
    @Transient
    private Long allCount;
    @Transient
    private Long doingCount;
    @Transient
    private Long pauseCount;
    @Transient
    private Long waitDoCount;
    @Transient
    private List<User> focusList = new ArrayList<>();

    //这个是客户的课题组合集
    @Transient
    private List<CrmSubjectGroup> subjectGroupList = new ArrayList<>();
}

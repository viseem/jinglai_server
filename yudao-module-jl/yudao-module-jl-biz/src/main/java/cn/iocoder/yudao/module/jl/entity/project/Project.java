package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import cn.iocoder.yudao.module.jl.entity.crmsubjectgroup.CrmSubjectGroup;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@SQLDelete(sql = "UPDATE jl_project_base SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Project extends BaseEntity{

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

    /**
     * 项目编号
     */
    @Column(name = "code", nullable = false )
    private String code;

    /**
     * 客户 id
     */
    @Column(name = "customer_id")
    private Long customerId;




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
    private LocalDateTime startDate;

    /**
     * 截止时间
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;


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
     * 项目售前负责人
     */
    @Column(name = "pre_manager_id")
    private Long preManagerId;

    /**
     * 项目售后负责人
     */
    @Column(name = "after_manager_id")
    private Long afterManagerId;
    /**
     * 实验负责人
     */
    @Column(name = "exper_id")
    private Long experId;

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
     * 转移记录json
     */
    @Column(name = "transfer_log")
    private String transferLog;

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
     * 当前安排单 id
     */
    @Column(name = "current_schedule_id")
    private Long currentScheduleId;

    /**
     * 当前安排单 id
     */
    @Column(name = "current_quotation_id",insertable =false, updatable = false)
    private Long currentQuotationId;


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


    /**
     * 级联出客户信息
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerSimple customer;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "pre_manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User preManager;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "after_manager_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User afterManager;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "exper_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User exper;

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
}

package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crmsubjectgroup.CrmSubjectGroup;
import cn.iocoder.yudao.module.jl.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectOnly")
@Table(name = "jl_project_base")
@SQLDelete(sql = "UPDATE jl_project_base SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class ProjectOnly extends BaseEntity{

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

    /*
     * 开展前审批，冗余字段
     * */
    @Column(name = "do_instance_id", nullable = false )
    private String doInstanceId;

    @Column(name = "do_apply_result", nullable = false )
    private String doApplyResult;

    @Column(name = "do_user_id", nullable = false )
    private Long doUserId;

    @Column(name = "do_audit_mark", nullable = false )
    private String doAuditMark;


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

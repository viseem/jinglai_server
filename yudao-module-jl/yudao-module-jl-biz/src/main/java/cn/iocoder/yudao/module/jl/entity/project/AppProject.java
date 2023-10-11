package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 项目管理 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AppProject")
@Table(name = "jl_project_base")
public class AppProject extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

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
     * 实验员
     */
    @Column(name = "exper_ids")
    private String experIds;

    /**
     * 参与者 ids，数组
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

    /**
     * 客户 id
     */
    @Column(name = "customer_id")
    private Long customerId;


    /**
     * 当前安排单 id
     */
    @Column(name = "current_schedule_id")
    private Long currentScheduleId;

    /**
     * 当前安排单
     */
/*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_schedule_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ProjectSchedule currentSchedule;
*/


    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Where(clause = "type = 'schedule'")
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private List<ProjectCategoryOnly> categoryList;

    @Transient
    private Integer allCount;
    @Transient
    private Integer completeCount;
    @Transient
    private Integer completePercent;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private List<ProjectDocument> documentList;

}

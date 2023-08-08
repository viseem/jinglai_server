package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目的实验名目 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectCategory")
@Table(name = "jl_project_category")
public class ProjectCategory extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 报价 id
     */
    @Column(name = "quote_id")
    private Long quoteId;

    /**
     * 项目 id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * JPA 级联出 project
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectOnly project;

    /**
     * 安排单 id
     */
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProjectSchedule schedule;

    /**
     * 类型，报价/安排单
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 所属实验室id
     */
    @Column(name = "lab_id")
    private Long labId;

    /**
     * JPA 级联出 lab
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "lab_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LaboratoryLab lab;

    /**
     * 名目的实验类型，动物/细胞/分子等
     */
    @Column(name = "category_type")
    private String categoryType;

    /**
     * 实验名目库的名目 id
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * 实验负责人
     */
    @Column(name = "operator_id")
    private Long operatorId;

    /**
     * 实验人员
     */
    @Column(name = "operator_ids")
    private String operatorIds;

    /**
     * JPA 级联出 user 实验负责人
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "operator_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User operator;

    /**
     * 客户需求
     */
    @Column(name = "demand")
    private String demand;

    /**
     * 开始日期
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 截止日期
     */
    @Column(name = "deadline")
    private String deadline;

    /**
     * 干扰项
     */
    @Column(name = "interference")
    private String interference;

    /**
     * 依赖项(json数组多个)
     */
    @Column(name = "depend_ids")
    private String dependIds;

    /**
     * 实验名目名字
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 从方案中选中的内容
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 当前实验的状态，未开展、开展中、数据审核、已完成
     */
    @Column(name = "stage")
    private String stage;


    /**
     * 原始数据
     */
    @Column(name = "raw_data")
    private String rawData;


    /**
     * 是否有反馈
     */
    @Column(name = "has_feedback", nullable = false)
    private Byte hasFeedback;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    @JoinColumn(name = "quote_id", insertable = false, updatable = false)
    private ProjectQuote quote;

    /**
     * 实验物资
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<ProjectSupply> supplyList;

    /**
     * 实验收费项
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private List<ProjectChargeitem> chargeList;

    /**
     * 实验SOP
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectSop> sopList;

    /**
     * 实验名目的附件
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectCategoryAttachment> attachmentList;

    /**
     * 实验名目的审批状态
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectCategoryApproval> approvalList;

    //审批的状态 通过 未通过
    @Column(name = "approval_stage")
    private String approvalStage;
    //申请变更的状态
    @Column(name = "request_stage")
    private String requestStage;
    //最新一个approval
    @Transient
    private ProjectCategoryApproval latestApproval;

    //实验记录
    /**
     * 查询实验记录列表
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "project_category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectCategoryLog> logs;
}

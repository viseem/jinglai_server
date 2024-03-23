package cn.iocoder.yudao.module.jl.entity.animal;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 动物饲养申请单 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AnimalFeedOrderSimple")
@Table(name = "jl_animal_feed_order")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnimalFeedOrderSimple extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 饲养单名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 品系品种
     */
    @Column(name = "breed", nullable = false )
    private String breed;

    /**
     * 周龄体重
     */
    @Column(name = "age", nullable = false )
    private String age;

    /**
     * 数量
     */
    @Column(name = "quantity", nullable = false )
    private Integer quantity;

    /**
     * 笼数
     */
    @Column(name = "cage_quantity", nullable = false )
    private Integer cageQuantity;

    /**
     * 雌
     */
    @Column(name = "female_count", nullable = false )
    private Integer femaleCount;

    /**
     * 雄
     */
    @Column(name = "male_count", nullable = false )
    private Integer maleCount;

    /**
     * 供应商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 供应商名字
     */
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 合格证号
     */
    @Column(name = "certificate_number")
    private String certificateNumber;

    /**
     * 许可证号
     */
    @Column(name = "license_number")
    private String licenseNumber;

    /**
     * 开始日期
     */
    @Column(name = "start_date", nullable = false )
    private String startDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date", nullable = false )
    private String endDate;

    /**
     * 有无传染性等实验
     */
    @Column(name = "has_danger", nullable = false )
    private Boolean hasDanger;

    /**
     * 水迷宫等设备需求id：先做成字典
     */
    @Column(name = "request_ids")
    private String requestIds;

    /**
     * 水迷宫等设备需求id：先做成字典
     */
    @Column(name = "condition_types")
    private String conditionTypes;

    /**
     * 饲养类型：普通饲养
     */
    @Column(name = "feed_type")
    private String feedType;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;


    /**
     * 流程实例id
     */
    @Column(name = "process_instance_id")
    private String processInstanceId;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private Long customerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerSimple customer;

    /**
     * 状态
     */
    @Column(name = "stage")
    private String stage;

    /**
     * 回复
     */
    @Column(name = "reply")
    private String reply;

    /**
     * 计费规则
     */
    @Column(name = "bill_rules")
    private String billRules;

    /**
     * 单价
     */
    @Column(name = "unit_fee")
    private Integer unitFee;

    /**
     * 入库备注
     */
    @Column(name = "in_mark")
    private String inMark;

    @Transient
    private AnimalFeedLog latestLog;
    @Transient
    private AnimalFeedStoreIn latestStore;
    @Transient
    private Integer Amount;

}

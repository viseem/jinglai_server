package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import cn.iocoder.yudao.module.jl.entity.laboratory.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目中的实验名目的物资项 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectSupply")
@Table(name = "jl_project_supply")
public class ProjectSupply extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 选中的实验名目 id
     */
    @Column(name = "project_category_id", nullable = false)
    private Long projectCategoryId;

    /**
     * 原始的实验名目 id
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;
    //    TODO 很大的问题
//    @ManyToOne(fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
//    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private Project project;

    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProjectOnly project;



    /**
     * 物资 id
     */
    @Column(name = "supply_id", nullable = false)
    private Long supplyId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 规则/单位
     */
    @Column(name = "fee_standard", nullable = false)
    private String feeStandard;

    /**
     * 单价
     */
    @Column(name = "unit_fee", nullable = false)
    private String unitFee;

    /**
     * 单量
     */
    @Column(name = "unit_amount", nullable = false)
    private Integer unitAmount;

    /**
     * 成本价
     */
    @Column(name = "buy_price", nullable = false)
    private Integer buyPrice = 0;

    /**
     * 数量
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * 物品来源
     */
    @Column(name = "source")
    private String source;

    /**
     * 采购总量
     */
    @Column(name = "procurement_quantity")
    private Integer procurementQuantity;

    /**
     * 库存总量
     */
    @Column(name = "inventory_quantity")
    private Integer inventoryQuantity;

    /**
     * 入库总量
     */
    @Column(name = "in_quantity")
    private Integer inQuantity;

    /**
     * 出库总量
     */
    @Column(name = "out_quantity")
    private Integer outQuantity;

    /**
     * 计算 已采购数量
     */
    @Transient
    private Integer procurementedQuantity;
    /**
     * 计算 已入库数量
     */
    @Transient
    private Integer inedQuantity;
    /**
     * 计算 已出库塑料
     */
    @Transient
    private Integer remainQuantity;

    @Column(name = "final_usage_num")
    private Integer finalUsageNum;

    @Column(name = "is_append")
    private Integer isAppend;

    /**
     * 剩余数量
     */
    @Transient
    private Integer outedQuantity;

    /**
     * 品牌
     */
    @Column(name = "brand")
    private String brand;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "project_category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProjectCategory category;


    /**
     * 出库items
     */
    @OneToMany(mappedBy = "projectSupply", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<SupplyOutItem> supplyOutItems;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_supply_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SELECT)
    private List<ProcurementItem> procurements;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_supply_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SELECT)
    private List<SupplySendInItem> sendIns;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_supply_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SELECT)
    private List<SupplyPickupItem> pickups;

    /**
     * 查询ProcurementItem
     */
/*    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_supply_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProcurementItem> procurements;*/

}

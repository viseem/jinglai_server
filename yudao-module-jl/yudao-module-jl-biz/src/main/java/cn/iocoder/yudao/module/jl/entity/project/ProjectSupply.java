package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目中的实验名目的物资项 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectSupply")
@Table(name = "jl_project_supply")
@SQLDelete(sql = "UPDATE jl_project_supply SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
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
    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;


    /**
     * 创建类型 报价 或者采购的时候 创建的
     */
    @Column(name = "create_type", nullable = false)
    private Integer createType=0;

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
     * 规格
     */
    @Column(name = "spec", nullable = false)
    private String spec;

    /**
     * 采购规格
     */
    @Column(name = "procurement_spec", nullable = false)
    private String procurementSpec;

    /**
     * 单价 这个是报价时的单价
     */
    @Column(name = "unit_fee", nullable = false)
    private BigDecimal unitFee;


    /**
     * 销售单价 这个每次采购时候 写的销售单价，可能会发生变化
     */
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;


    /**
     * 原单价
     */
    @Column(name = "origin_price", nullable = false)
    private BigDecimal originPrice;

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

    @Column(name = "sort", nullable = false)
    private Integer sort;

    /*
    * 官网价
    * */
    private Integer officialPrice = 0;


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

    @Transient
    private InventoryStoreIn latestStoreLog;

    /**
     * 品牌
     */
    @Column(name = "brand")
    private String brand;

    /**
     * 目录号
     */
    @Column(name = "catalog_number")
    private String catalogNumber;

    /*
    * 货号
    * */
    @Column(name = "product_code")
    private String productCode;

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

    /**
     * 收货地址
     */
    @Column(name = "receive_room_name")
    private String receiveRoomName;

    /**
     * 内部备注
     */
    @Column(name = "internal_mark")
    private String internalMark;

    /**
     * 当前单价
     */
    @Column(name = "current_price")
    private BigDecimal currentPrice;

    /**
     * 当前数量
     */
    @Column(name = "current_quantity")
    private Integer currentQuantity;

    /**
     * 当前规格
     */
    @Column(name = "current_spec")
    private String currentSpec;

    /**
     * 当前品牌
     */
    @Column(name = "current_brand")
    private String currentBrand;

    /**
     * 当前目录号
     */
    @Column(name = "current_catalog_number")
    private String currentCatalogNumber;

    @Column(columnDefinition = "boolean default false",name="deleted_status")
    private Boolean deletedStatus = false;



    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_supply_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SELECT)
    private List<ProcurementItem> procurements;

    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProjectSimple project;

    /**
     * 入库记录
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "project_supply_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<InventoryStoreIn> storeLogs = new ArrayList<>();

    /**
     * 出库记录
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "project_supply_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<InventoryStoreOut> outLogs = new ArrayList<>();

}

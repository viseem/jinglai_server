package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 出库申请明细 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplyOutItem")
@Table(name = "jl_inventory_supply_out_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplyOutItem extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 出库申请表 id
     */
    @Column(name = "supply_out_id", nullable = false )
    private Long supplyOutId;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name="supply_out_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private SupplyOut supplyOut;

    /**
     * 物资 id
     */
    @Column(name = "project_supply_id", nullable = false )
    private Long projectSupplyId;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name="project_supply_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProjectSupply projectSupply;

    /**
     * 物资库的物资 id
     */
    @Column(name = "supply_id", nullable = false )
    private Long supplyId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 规则/单位
     */
    @Column(name = "fee_standard", nullable = false )
    private String feeStandard;

    /**
     * 单价
     */
    @Column(name = "unit_fee", nullable = false )
    private String unitFee;

    /**
     * 单量
     */
    @Column(name = "unit_amount", nullable = false )
    private Integer unitAmount;

    /**
     * 数量
     */
    @Column(name = "quantity", nullable = false )
    private Integer quantity;

    /**
     * 申请出库数量
     */
    @Column(name = "out_quantity", nullable = false )
    private Integer outQuantity;

    /**
     * 已出库数量
     */
    @Column(name = "store_out", nullable = false )
    private Integer storeOut;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

}

package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
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
 * 实验产品入库明细 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductInItem")
@Table(name = "jl_inventory_product_in_item")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductInItem extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 实验产品入库表 id
     */
    @Column(name = "product_in_id", nullable = false)
    private Long productInId;


    /**
     * JPA 级联出 productIn
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "product_in_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductInOnly productIn;

    /**
     * 查询款项列表
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductSendItem> productSendItems = new ArrayList<>();

    /**
     * 产自实验物资的 id
     */
    @Column(name = "source_supply_id")
    private Long sourceSupplyId;

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
     * 数量
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * 入库数量
     */
    @Column(name = "in_quantity", nullable = false)
    private Integer inQuantity;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 房间 id
     */
    @Column(name = "room_id")
    private Long roomId;

    /**
     * 储存器材 id
     */
    @Column(name = "container_id")
    private Long containerId;

    /**
     * 区域位置 id
     */
    @Column(name = "zoom_id")
    private Long zoomId;

    /**
     * 有效期
     */
    @Column(name = "valid_date")
    private String validDate;

    /**
     * 存储温度
     */
    @Column(name = "temperature")
    private String temperature;

    @Transient
    private Integer sendedQuantity;

    @Transient
    private String inStatus;
}

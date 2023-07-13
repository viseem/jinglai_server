package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
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
 * 实验产品寄送明细 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductSendItem")
@Table(name = "jl_inventory_product_send_item")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductSendItem extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 实验产品寄送表 id
     */
    @Column(name = "product_send_id", nullable = false )
    private Long productSendId;


    @ManyToOne
    @JoinColumn(name="product_send_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProductSend productSend;


    /**
     * 产品 id
     */
    @Column(name = "product_id", nullable = false )
    private Long productId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductInItemOnly productInItem;

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
     * 数出库数量
     */
    @Column(name = "out_quantity", nullable = false )
    private Integer outQuantity = 0;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 有效期
     */
    @Column(name = "valid_date", nullable = false )
    private String validDate;

    /**
     * 存储温度
     */
    @Column(name = "temperature", nullable = false )
    private String temperature;

}

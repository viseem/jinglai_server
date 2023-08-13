package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
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
import java.time.LocalDateTime;

/**
 * 出库记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InventoryStoreOut")
@Table(name = "jl_inventory_store_out")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InventoryStoreOut extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目 id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 实验物资名称 id
     */
    @Column(name = "project_supply_id")
    private Long projectSupplyId;

    /**
     * 出库数量
     */
    @Column(name = "out_quantity")
    private Integer outQuantity;

    /**
     * 类型，产品入库，客户寄来
     */
    @Column(name = "type")
    private String type;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 产品入库，客户寄来的 id
     */
    @Column(name = "ref_id")
    private Long refId;

    /**
     * 产品入库，客户寄来的子元素 id
     */
    @Column(name = "ref_item_id")
    private Long refItemId;

    /**
     * 保存温度
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 有效截止期
     */
    @Column(name = "valid_date")
    private LocalDateTime validDate;

    /**
     * 申请人
     */
    @Column(name = "apply_user")
    private Long applyUser;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "apply_user",  insertable = false, updatable = false)
    private User applier;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator",  insertable = false, updatable = false)
    private User user;

}

package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 取货单申请明细 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplyPickupItem")
@Table(name = "jl_project_supply_pickup_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplyPickupItem extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 取货单申请id
     */
    @Column(name = "supply_pickup_id", nullable = false )
    private Long supplyPickupId;

    /**
     * 项目物资 id
     */
    @Column(name = "project_supply_id", nullable = false )
    private Long projectSupplyId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "product_code")
    private String productCode;

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

    @Column(name = "spec", nullable = false)
    private String spec;

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
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 有效期
     */
    @Column(name = "valid_date")
    private String validDate;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;


    /**
     * 签收的数量
     */
    @Column(name = "chenck_in_quantity")
    private Integer checkInQuantity = 0;

    /**
     * 入库的数量
     */
    @Column(name = "in_quantity")
    private Integer inQuantity = 0;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "container_id")
    private Long containerId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "temperature")
    private String temperature;
}

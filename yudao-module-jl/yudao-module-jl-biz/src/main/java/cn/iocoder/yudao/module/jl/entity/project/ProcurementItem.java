package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
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
 * 项目采购单申请明细 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProcurementItem")
@Table(name = "jl_project_procurement_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProcurementItem extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 采购单id
     */
    @Column(name = "procurement_id", nullable = false)
    private Long procurementId;

    /**
     * 项目物资 id
     */
    @Column(name = "project_supply_id", nullable = false)
    private Long projectSupplyId;

    @Column(name = "schedule_id")
    private Long scheduleId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 采购规则/单位
     */
    @Column(name = "fee_standard", nullable = false)
    private String feeStandard;

    /**
     * 单价
     */
    @Column(name = "unit_fee")
    private String unitFee;

    /**
     * 单量
     */
    @Column(name = "unit_amount")
    private Integer unitAmount;



    /**
     * 采购数量
     */
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * 供货商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 原价
     */
    @Column(name = "buy_price")
    private Integer buyPrice = 0;

    /**
     * 销售价
     */
    @Column(name = "sale_price")
    private Integer salePrice;

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
     * 品牌
     */
    @Column(name = "brand")
    private String brand;

    /**
     * 目录号
     */
    @Column(name = "catalog_number")
    private String catalogNumber;

    /**
     * 货期
     */
    @Column(name = "delivery_date")
    private String deliveryDate;

    /**
     * 状态:等待采购信息、等待打款、等待采购、等待签收、等待入库
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

    @Column(name = "temperature")
    private String temperature;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Supplier supplier;
}

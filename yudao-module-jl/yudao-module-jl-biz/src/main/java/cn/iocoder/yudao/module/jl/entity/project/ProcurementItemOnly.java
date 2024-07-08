package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContractOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 项目采购单申请明细 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProcurementItemOnly")
@Table(name = "jl_project_procurement_item")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProcurementItemOnly extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 实验室id
     */
    @Column(name = "lab_id", nullable = false )
    private Long labId;

    /**
     * 采购类型 项目 实验室 行政
     */
    @Column(name = "procurement_type", nullable = false )
    private Integer procurementType;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "purchase_contract_id")
    private Long purchaseContractId;
    @Column(name = "unit")
    private String unit;
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
     * 来源
     */
    @Column(name = "source", nullable = false)
    private String source;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 入库数量
     */
    @Column(name = "ined_quantity")
    private BigDecimal inedQuantity;

    /**
     * 出库数量
     */
    @Column(name = "outed_quantity")
    private BigDecimal outedQuantity;

    /*
    * 库存
    * */
    @Transient
    private BigDecimal stockQuantity;

    public BigDecimal getStockQuantity() {
        return (inedQuantity==null?BigDecimal.ZERO:inedQuantity).subtract(outedQuantity==null?BigDecimal.ZERO:outedQuantity);
    }

    /**
     * 采购规则/单位
     */
    @Column(name = "fee_standard", nullable = false)
    private String feeStandard;

    @Column(name = "spec", nullable = false)
    private String spec;

    @Transient
    private String procurementSpec;
    /**
     * 单价
     */
    @Column(name = "unit_fee")
    private BigDecimal unitFee;

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
    @Column(name = "origin_price")
    private BigDecimal originPrice = BigDecimal.ZERO;

    /**
     * 采购价
     */
    @Column(name = "buy_price")
    private BigDecimal buyPrice = BigDecimal.ZERO;

    /**
     * 运费
     */
    @Column(name = "freight")
    private BigDecimal freight = BigDecimal.valueOf(0);

    /**
     * 销售价
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 付款周期
     */
    @Column(name = "payment_cycle")
    private String paymentCycle;

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
     * 货号
     */
    @Column(name = "product_code")
    private String productCode;

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

    @Column(name = "receive_room_id")
    private Long receiveRoomId;

    @Column(name = "receive_room_name")
    private String receiveRoomName;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "container_id")
    private Long containerId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "refund_quantity")
    private Long refundQuantity;
    @Column(name = "refund_amount")
    private Long refundAmount;
    @Column(name = "refund_receipts")
    private String refundReceipts;

    //预计到货日期
    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    /**
     * 采购单同意时间
     */
    @Column(name = "purchase_accept_time")
    private LocalDateTime purchaseAcceptTime;

    /**
     * 购销合同同意时间
     */
    @Column(name = "contract_accept_time")
    private LocalDateTime contractAcceptTime;

}

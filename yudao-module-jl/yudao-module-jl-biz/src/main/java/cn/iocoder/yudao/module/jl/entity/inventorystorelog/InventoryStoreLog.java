package cn.iocoder.yudao.module.jl.entity.inventorystorelog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import lombok.*;

import java.math.BigDecimal;
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
 * 物品出入库日志 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InventoryStoreLog")
@Table(name = "jl_inventory_store_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InventoryStoreLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 物品来源
     */
    @Column(name = "source", nullable = false )
    private String source;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 目录号
     */
    @Column(name = "catalog_number", nullable = false )
    private String catalogNumber;

    /**
     * 品牌
     */
    @Column(name = "brand", nullable = false )
    private String brand;

    /**
     * 规格
     */
    @Column(name = "spec", nullable = false )
    private String spec;

    /**
     * 单位
     */
    @Column(name = "unit", nullable = false )
    private String unit;

    /**
     * 变更数量
     */
    @Column(name = "change_num", nullable = false )
    private BigDecimal changeNum = BigDecimal.ZERO;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 房间id
     */
    @Column(name = "room_id")
    private Long roomId;

    /**
     * 容器id
     */
    @Column(name = "container_id")
    private Long containerId;

    /**
     * 位置id
     */
    @Column(name = "place_id")
    private Long placeId;

    /**
     * 位置详情
     */
    @Column(name = "location")
    private String location;

    /**
     * 明细的id
     */
    @Column(name = "source_item_id", nullable = false )
    private Long sourceItemId;

    /**
     * 项目物资的id
     */
    @Column(name = "project_supply_id")
    private Long projectSupplyId;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 购销合同id
     */
    @Column(name = "purchase_contract_id")
    private Long purchaseContractId;

    /**
     * 采购单id
     */
    @Column(name = "procurement_id")
    private Long procurementId;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private LocalDateTime operateTime;

    /**
     * 操作者id
     */
    @Column(name = "operator_id")
    private Long operatorId;

    /**
     * 级联
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "operator_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}

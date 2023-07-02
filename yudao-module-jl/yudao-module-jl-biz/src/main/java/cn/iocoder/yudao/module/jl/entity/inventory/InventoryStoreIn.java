package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 入库记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InventoryStoreIn")
@Table(name = "jl_inventory_store_in")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InventoryStoreIn extends BaseEntity {

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
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 实验物资名称 id
     */
    @Column(name = "project_supply_id")
    private Long projectSupplyId;

    /**
     * 入库数量
     */
    @Column(name = "in_quantity")
    private Integer inQuantity;

    /**
     * 类型，采购，寄送，自取
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
     * 采购，寄送，自取的 id
     */
    @Column(name = "ref_id")
    private Long refId;

    /**
     * 采购，寄送，自取的子元素 id
     */
    @Column(name = "ref_item_id")
    private Long refItemId;

    /**
     * 库房 id
     */
    @Column(name = "room_id")
    private Long roomId;

    /**
     * 柜子id
     */
    @Column(name = "container_id")
    private Long containerId;

    /**
     * 位置id
     */
    @Column(name = "place_id")
    private Long placeId;

    /**
     * 保存温度
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 有效截止期
     */
    @Column(name = "valid_date")
    private String validDate;

}

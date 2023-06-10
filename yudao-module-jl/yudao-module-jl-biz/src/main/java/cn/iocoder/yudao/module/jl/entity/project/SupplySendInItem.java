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
 * 物资寄来单申请明细 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplySendInItem")
@Table(name = "jl_project_supply_send_in_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplySendInItem extends BaseEntity {

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
     * 实验名目库的名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 物资寄来申请表id
     */
    @Column(name = "supply_send_in_id", nullable = false )
    private Long supplySendInId;

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

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

}

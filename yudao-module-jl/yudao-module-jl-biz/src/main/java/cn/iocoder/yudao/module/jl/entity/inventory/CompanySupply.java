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

/**
 * 公司实验物资库存 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CompanySupply")
@Table(name = "jl_inventory_company_supply")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CompanySupply extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

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
     * 物资 id
     */
    @Column(name = "supply_id")
    private Long supplyId;

    /**
     * 存储位置
     */
    @Column(name = "location")
    private String location;

    /**
     * 项目物资id
     */
    @Column(name = "project_supply_id")
    private Long projectSupplyId;

    /**
     * 所属客户
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 所属项目
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 所属类型：公司、客户
     */
    @Column(name = "owner_type", nullable = false )
    private String ownerType;

    /**
     * 单价
     */
    @Column(name = "unit_fee")
    private String unitFee;

    /**
     * 有效期
     */
    @Column(name = "valid_date")
    private String validDate;

    /**
     * 物资快照名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 物资快照地址
     */
    @Column(name = "file_url")
    private String fileUrl;

}

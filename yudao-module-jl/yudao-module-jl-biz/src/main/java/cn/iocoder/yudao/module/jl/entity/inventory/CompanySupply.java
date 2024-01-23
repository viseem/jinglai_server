package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerOnly customer;

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

    /*
    * 品牌
    * */
    @Column(name = "brand")
    private String brand;

    /*
     * 货号
     * */
    @Column(name = "product_code")
    private String productCode;

    //------级联信息
    /*
    * 级联项目
    * */
    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;
    /*
    * 级联项目物资
    * */
    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_supply_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSupply projectSupply;

}

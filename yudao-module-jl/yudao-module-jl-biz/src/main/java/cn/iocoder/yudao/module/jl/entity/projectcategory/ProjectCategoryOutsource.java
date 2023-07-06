package cn.iocoder.yudao.module.jl.entity.projectcategory;

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
 * 项目实验委外 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectCategoryOutsource")
@Table(name = "jl_project_category_outsource")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectCategoryOutsource extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 项目的实验名目id
     */
    @Column(name = "project_category_id", nullable = false )
    private Long projectCategoryId;

    /**
     * 类型：项目、实验、其它
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 外包内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 外包供应商id
     */
    @Column(name = "category_supplier_id")
    private Integer categorySupplierId;

    /**
     * 供应商报价
     */
    @Column(name = "supplier_price", nullable = false )
    private Integer supplierPrice;

    /**
     * 销售价格
     */
    @Column(name = "sale_price", nullable = false )
    private Integer salePrice;

    /**
     * 购买价格
     */
    @Column(name = "buy_price")
    private Integer buyPrice;

    /**
     * 凭证名字
     */
    @Column(name = "proof_name", nullable = false )
    private String proofName;

    /**
     * 凭证地址 
     */
    @Column(name = "proof_url", nullable = false )
    private String proofUrl;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

}

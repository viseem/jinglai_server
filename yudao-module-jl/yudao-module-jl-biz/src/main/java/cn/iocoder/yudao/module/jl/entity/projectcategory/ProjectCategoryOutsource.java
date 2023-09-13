package cn.iocoder.yudao.module.jl.entity.projectcategory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
     * JPA 级联出 实验
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectCategoryOnly projectCategory;

    @Column(name = "schedule_id")
    private Long scheduleId;

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
     * JPA 级联出 委外供应商
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "category_supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectCategorySupplier supplier;


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
    private Integer buyPrice = 0;

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


    /**
     * 原始数据
     */
    @Column(name = "rawdata")
    private String rawdata;

    /**
     * 备注
     */
    @Column(name = "record")
    private String record;

    /**
     * 附件 json
     */
    @Column(name = "files")
    private String files;

    //--------额外信息 不在表中
    /*
    * 已打款金额
    * */
    @Transient
    private BigDecimal paidPrice;

    // ---------------------级联表

    /**
     * 查询款项列表
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @Where(clause = "type = '1'")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<FinancePayment> payments = new ArrayList<>();


}

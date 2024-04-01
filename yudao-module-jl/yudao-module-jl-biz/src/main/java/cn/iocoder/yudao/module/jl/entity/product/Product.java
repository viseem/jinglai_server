package cn.iocoder.yudao.module.jl.entity.product;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroupOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * 产品库 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Product")
@Table(name = "jl_product")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product extends BaseEntity {

    /**
     * ID
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
     * 分类
     */
    @Column(name = "cate_id")
    private Long cateId;

    /**
     * 状态
     */
    @Column(name = "status", nullable = false )
    private String status;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * pi组
     */
    @Column(name = "pi_group_id")
    private Long piGroupId;

    /**
     * 实验负责人
     */
    @Column(name = "exper_id")
    private Long experId;

    /**
     * 信息负责人
     */
    @Column(name = "info_user_id")
    private Long infoUserId;

    /**
     * 实施主体
     */
    @Column(name = "subject")
    private String subject;

    /**
     * 供应商id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 标准价格
     */
    @Column(name = "standard_price")
    private BigDecimal standardPrice;

    /**
     * 成本价格
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 销售价格
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 竞品价格
     */
    @Column(name = "compete_price")
    private BigDecimal competePrice;

    /**
     * 优惠价格
     */
    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    /**
     * 已售金额
     */
    @Column(name = "sold_amount")
    private BigDecimal soldAmount;

    /**
     * 已售份数
     */
    @Column(name = "sold_count")
    private Integer soldCount;

    /**
     * 存量
     */
    @Column(name = "stock_count")
    private Integer stockCount;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "exper_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User exper;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "info_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User infoUser;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User createUser;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "cate_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductCate cate;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "pi_group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SubjectGroupOnly subjectGroup;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductUser> userList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductDevice> deviceList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductSop> sopList;

    /*
     * 级联附件
     * */
    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "type = 'JL_PRODUCT'")
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CommonAttachment> attachmentList = new ArrayList<>();
}

package cn.iocoder.yudao.module.jl.entity.product;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 产品库 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductOnly")
@Table(name = "jl_product")
@SQLDelete(sql = "UPDATE jl_product SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductOnly extends BaseEntity {

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
     * 英文名称
     */
    @Column(name = "name_en", nullable = false )
    private String nameEn;

    /**
     * 简称
     */
    @Column(name = "name_short", nullable = false )
    private String nameShort;

    /**
     * 规格
     */
    @Column(name = "spec", nullable = false )
    private String spec;

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

    /**
     * 技术类型
     */
    @Column(name = "tech_type")
    private Integer techType;

    /**
     * 产品资料内容
     */
    @Column(name = "data_content")
    private String dataContent;

    /**
     * 交付标准内容
     */
    @Column(name = "result_content")
    private String resultContent;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;


}

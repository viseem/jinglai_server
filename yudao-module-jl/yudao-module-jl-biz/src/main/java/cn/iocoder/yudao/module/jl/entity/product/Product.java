package cn.iocoder.yudao.module.jl.entity.product;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @Column(name = "cate")
    private String cate;

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

}

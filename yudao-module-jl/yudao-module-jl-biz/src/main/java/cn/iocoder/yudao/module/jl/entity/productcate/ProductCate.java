package cn.iocoder.yudao.module.jl.entity.productcate;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 产品库分类 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductCate")
@Table(name = "jl_product_cate")
@SQLDelete(sql = "UPDATE jl_product_cate SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductCate extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 说明
     */
    @Column(name = "mark")
    private String mark;


    /**
     * 通过mappedBy指定父项，查询子项
     */
    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductCate> childList;


    /**
     * 配置字段parent_id关联主键（默认），查询父项
     * 可以用referencedColumnName指定关联的字段
     */
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private ProductCate parent;

}

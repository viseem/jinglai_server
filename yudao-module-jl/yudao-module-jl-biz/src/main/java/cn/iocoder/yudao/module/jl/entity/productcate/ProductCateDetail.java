package cn.iocoder.yudao.module.jl.entity.productcate;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品库分类 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductCateDetail")
@Table(name = "jl_product_cate")
@SQLDelete(sql = "UPDATE jl_product_cate SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductCateDetail extends BaseEntity {

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
     * 标签
     */
    @Column(name = "tag_ids")
    private String tagIds;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 详细介绍
     */
    @Column(name = "content")
    private String content;



    /*
     * 级联附件
     * */
    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "type = 'PRODUCT_CATE'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CommonAttachment> attachmentList = new ArrayList<>();

}
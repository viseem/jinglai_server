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
 * 实验产品入库 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductIn")
@Table(name = "jl_inventory_product_in")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductIn extends BaseEntity {

    /**
     * 岗位ID
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
    @Column(name = "category_id", nullable = false )
    private Long categoryId;

    /**
     * 实验名目 id
     */
    @Column(name = "project_category_id", nullable = false )
    private Long projectCategoryId;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;


    @OneToMany
    @JoinColumn(name = "product_in_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ProductInItem> items;

}
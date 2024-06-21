package cn.iocoder.yudao.module.jl.entity.taskproduct;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.product.ProductSelector;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 任务产品中间 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TaskProduct")
@Table(name = "jl_task_product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TaskProduct extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 任务id
     */
    @Column(name = "task_id", nullable = false )
    private Long taskId;

    /**
     * 产品id
     */
    @Column(name = "product_id", nullable = false )
    private Long productId;

    /**
     * 级联
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductSelector product;

}

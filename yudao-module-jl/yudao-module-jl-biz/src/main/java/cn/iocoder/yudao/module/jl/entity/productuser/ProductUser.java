package cn.iocoder.yudao.module.jl.entity.productuser;

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
 * 产品库人员 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductUser")
@Table(name = "jl_product_user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductUser extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 产品
     */
    @Column(name = "product_id", nullable = false )
    private Long productId;

    /**
     * 用户
     */
    @Column(name = "user_id", nullable = false )
    private Long userId;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

}

package cn.iocoder.yudao.module.jl.entity.animal;

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
 * 动物饲养鼠牌 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AnimalFeedCard")
@Table(name = "jl_animal_feed_card")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnimalFeedCard extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 饲养单id
     */
    @Column(name = "feed_order_id", nullable = false )
    private Long feedOrderId;

    /**
     * 序号
     */
    @Column(name = "seq")
    private Integer seq;

    /**
     * 品系品种
     */
    @Column(name = "breed", nullable = false )
    private String breed;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 性别
     */
    @Column(name = "gender", nullable = false )
    private Byte gender;

    /**
     * 数量
     */
    @Column(name = "quantity", nullable = false )
    private Integer quantity;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private Long customerId;

}

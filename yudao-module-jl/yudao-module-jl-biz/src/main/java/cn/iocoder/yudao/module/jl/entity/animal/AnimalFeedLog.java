package cn.iocoder.yudao.module.jl.entity.animal;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * 动物饲养日志 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AnimalFeedLog")
@Table(name = "jl_animal_feed_log")
@Builder
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnimalFeedLog extends BaseEntity {

    @Override
    public String toString() {
        return "AnimalFeedLog{" +
                "id=" + id +
                ", feedOrderId=" + feedOrderId +
                ", createTime=" + getCreateTime() + // Assuming createTime is a LocalDateTime or similar
                ", changeQuantity=" + changeQuantity +
                ", changeCageQuantity=" + changeCageQuantity +
                ", quantity=" + quantity +
                ", cageQuantity=" + cageQuantity +
                '}';
    }

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
     * 变更数量
     */
    @Column(name = "change_quantity", nullable = false )
    private Integer changeQuantity;

    /**
     * 变更笼数
     */
    @Column(name = "change_cage_quantity", nullable = false )
    private Integer changeCageQuantity;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 变更附件
     */
    @Column(name = "files")
    private String files;

    /**
     * 变更位置
     */
    @Column(name = "stores")
    private String stores;

    /**
     * 变更后数量
     */
    @Column(name = "quantity", nullable = false )
    private Integer quantity;

    /**
     * 变更后笼数
     */
    @Column(name = "cage_quantity", nullable = false )
    private Integer cageQuantity;

    /**
     * 笼位id
     */
    @Column(name = "box_id", nullable = false )
    private Long boxId;

    /**
     * 变更后数量
     */
    @Column(name = "box_quantity", nullable = false )
    private Integer boxQuantity;


    @Transient
    private Integer rowAmount;

    @Transient
    private String dateStr;

    @Transient
    private String timeStr;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "feed_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AnimalFeedOrderSimple feedOrder;

}

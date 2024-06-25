package cn.iocoder.yudao.module.jl.entity.taskarrangerelation;

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
 * 任务安排关系 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TaskArrangeRelation")
@Table(name = "jl_task_arrange_relation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TaskArrangeRelation extends BaseEntity {

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
     * 收费项id
     */
    @Column(name = "charge_item_id", nullable = false )
    private Long chargeItemId;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Long productId;

}

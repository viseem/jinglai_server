package cn.iocoder.yudao.module.jl.entity.taskarrangerelation;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
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
     * 级联
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "task_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CommonTask task;


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

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 报价id
     */
    @Column(name = "quotation_id")
    private Long quotationId;

}

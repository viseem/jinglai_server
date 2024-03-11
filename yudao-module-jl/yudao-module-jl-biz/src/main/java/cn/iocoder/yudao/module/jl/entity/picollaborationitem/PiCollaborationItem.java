package cn.iocoder.yudao.module.jl.entity.picollaborationitem;

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

/**
 * pi组协作明细 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PiCollaborationItem")
@Table(name = "jl_pi_collaboration_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PiCollaborationItem extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 协助的主表id
     */
    @Column(name = "collaboration_id", nullable = false )
    private Long collaborationId;

    /**
     * pi组id
     */
    @Column(name = "pi_id", nullable = false )
    private Long piId;

    /**
     * 占比
     */
    @Column(name = "percent")
    private BigDecimal percent;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

}

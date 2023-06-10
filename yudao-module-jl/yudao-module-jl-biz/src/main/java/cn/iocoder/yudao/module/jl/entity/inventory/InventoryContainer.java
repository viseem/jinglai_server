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
 * 库管存储容器 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InventoryContainer")
@Table(name = "jl_inventory_container")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InventoryContainer extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 库房id：可以用字典
     */
    @Column(name = "room_id", nullable = false )
    private Integer roomId;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 所在位置
     */
    @Column(name = "place")
    private String place;

    /**
     * 负责人
     */
    @Column(name = "guardian_user_id")
    private String guardianUserId;

    /**
     * 备注描述
     */
    @Column(name = "mark")
    private String mark;

}

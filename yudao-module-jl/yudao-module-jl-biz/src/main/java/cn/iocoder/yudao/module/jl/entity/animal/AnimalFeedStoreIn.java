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
 * 动物饲养入库 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AnimalFeedStoreIn")
@Table(name = "jl_animal_feed_store_in")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnimalFeedStoreIn extends BaseEntity {

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
     * 房间id
     */
    @Column(name = "room_id")
    private Long roomId;

    /**
     * 架子ids
     */
    @Column(name = "shelf_ids")
    private String shelfIds;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 位置
     */
    @Column(name = "location")
    private String location;

    /**
     * 位置code
     */
    @Column(name = "location_code")
    private String locationCode;

}

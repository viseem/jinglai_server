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
 * 动物笼位 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AnimalBox")
@Table(name = "jl_animal_box")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AnimalBox extends BaseEntity {

    /**
     * ``
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 编码
     */
    @Column(name = "code", nullable = false )
    private String code;

    /**
     * 位置
     */
    @Column(name = "location")
    private String location;

    /**
     * 容量
     */
    @Column(name = "capacity", nullable = false )
    private Integer capacity;

    /**
     * 现有
     */
    @Column(name = "quantity")
    private Integer quantity = 0;

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

    /**
     * 架子id
     */
    @Column(name = "shelf_id", nullable = false )
    private Long shelfId;

    /**
     * 房间id
     */
    @Column(name = "room_id", nullable = false )
    private Long roomId;

    /**
     * 行索引
     */
    @Column(name = "row_index")
    private Integer rowIndex;

    /**
     * 列索引
     */
    @Column(name = "col_index")
    private Integer colIndex;

    /**
     * 饲养单
     */
    @Column(name = "feed_order_id", nullable = false )
    private Long feedOrderId;


    /**
     *
     */
    @Column(name = "feed_order_code")
    private String feedOrderCode;
    /**
     *
     */
    @Column(name = "feed_order_name")
    private String feedOrderName;
    /**
     *
     */
    @Column(name = "project_name")
    private String projectName;
    /**
     *
     */
    @Column(name = "customer_name")
    private String customerName;
}

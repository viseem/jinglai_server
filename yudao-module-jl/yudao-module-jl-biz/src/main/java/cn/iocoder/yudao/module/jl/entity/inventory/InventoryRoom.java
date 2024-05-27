package cn.iocoder.yudao.module.jl.entity.inventory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
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
 * 库管房间号 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InventoryRoom")
@Table(name = "jl_inventory_room")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InventoryRoom extends BaseEntity {

    /**
     * ID
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
     * 负责人
     */
    @Column(name = "guardian_user_id")
    private Long guardianUserId;

    /**
     * 备注描述
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 收件人
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 收件人电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 收件地址
     */
    @Column(name = "receive_address")
    private String receiveAddress;


    /**
     * 级联
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "guardian_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User manager;

}

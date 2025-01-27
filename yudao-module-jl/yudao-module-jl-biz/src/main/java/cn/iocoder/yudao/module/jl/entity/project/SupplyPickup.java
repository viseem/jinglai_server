package cn.iocoder.yudao.module.jl.entity.project;

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
 * 取货单申请 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplyPickup")
@Table(name = "jl_project_supply_pickup")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplyPickup extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User receiver;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * 实验名目库的名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 取货单号
     */
    @Column(name = "code")
    private String code;

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
     * 取货时间
     */
    @Column(name = "send_date")
    private LocalDateTime sendDate;

    /**
     * 取货人
     */
    @Column(name = "user_id", nullable = false )
    private Long userId;

    /**
     * 取货地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 联系人姓名
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系人电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    @OneToMany
    @JoinColumn(name = "supply_pickup_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<SupplyPickupItem> items;


    /**
     * 是否有要签收的
     */
    @Column(name = "wait_check_in")
    private Boolean waitCheckIn;

    /**
     * 是否有要入库的
     */
    @Column(name = "wait_store_in")
    private Boolean waitStoreIn;

    @Column(name = "shipment_codes")
    private String shipmentCodes;
}

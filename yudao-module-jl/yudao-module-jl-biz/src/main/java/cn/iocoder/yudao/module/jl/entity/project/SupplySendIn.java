package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplySendInItemCreateReqVO;
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
 * 物资寄来单申请 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplySendIn")
@Table(name = "jl_project_supply_send_in")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplySendIn extends BaseEntity {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * 实验名目库的名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 寄来单号
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
     * 寄来时间
     */
    @Column(name = "send_date")
    private String sendDate;

    /**
     * 收货地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 收货人
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    @OneToMany
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "supply_send_in_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<SupplySendInItem> items;


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

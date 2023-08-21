package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

/**
 * 项目采购单申请 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Procurement")
@Table(name = "jl_project_procurement")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Procurement extends BaseEntity {

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
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;

    /**
     * 实验名目库的名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 采购单号
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
     * 采购人员回复
     */
    @Column(name = "reply")
    private String reply;

    /**
     * 采购发起时间
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 签收陪审人
     */
    @Column(name = "check_user_id")
    private Long checkUserId;

    /**
     * 收货地址
     */
    @Column(name = "address")
    private String address;

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

    @Column(name = "schedule_id")
    private Long scheduleId;

    /**
     * 收货人id
     */
    @Column(name = "receiver_user_id")
    private String receiverUserId;

    /**
     * 流程实例id
     */
    @Column(name = "process_instance_id")
    private String processInstanceId;

    @OneToMany(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "procurement_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<ProcurementItem> items;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "check_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User checkUser;


    @OneToMany(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "procurement_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<ProcurementShipment> shipments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "procurement_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<ProcurementPayment> payments = new ArrayList<>();
}

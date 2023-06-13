package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.SupplySendInItemCreateReqVO;
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
     * 寄来物流单号
     */
    @Column(name = "shipment_number")
    private String shipmentNumber;

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
    @JoinColumn(name = "supply_send_in_id", referencedColumnName = "id", insertable = false, updatable = false)
    private List<SupplySendInItem> items;
}

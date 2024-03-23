package cn.iocoder.yudao.module.jl.entity.visitappointment;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;

/**
 * 晶莱到访预约 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "VisitAppointment")
@Table(name = "jl_visit_appointment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VisitAppointment extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 陪同人员id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 设备id
     */
    @Column(name = "device_id")
    private Long deviceId;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 到访时间
     */
    @Column(name = "visit_time", nullable = false )
    private LocalDateTime visitTime;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    /**
     * 设备预约记录id
     */
    @Column(name = "device_log_id", nullable = false )
    private Long deviceLogId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "device_log_id", insertable = false, updatable = false)
    private AssetDeviceLog deviceLog;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerSimple customer;

}

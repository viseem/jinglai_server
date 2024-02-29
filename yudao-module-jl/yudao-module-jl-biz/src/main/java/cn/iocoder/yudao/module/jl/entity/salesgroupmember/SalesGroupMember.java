package cn.iocoder.yudao.module.jl.entity.salesgroupmember;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.salesgroup.SalesGroup;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.math.BigDecimal;
import java.math.BigDecimal;

/**
 * 销售分组成员 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SalesGroupMember")
@Table(name = "jl_sales_group_member")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SalesGroupMember extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 分组id
     */
    @Column(name = "group_id", nullable = false )
    private Long groupId;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false )
    private Long userId;

    /**
     * 月度回款目标
     */
    @Column(name = "month_refund_kpi")
    private BigDecimal monthRefundKpi;

    /**
     * 月度订单目标
     */
    @Column(name = "month_order_kpi")
    private BigDecimal monthOrderKpi;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SalesGroup group;

    @Transient
    private BigDecimal refundAmount = BigDecimal.ZERO;

    @Transient
    private Integer refundAmountPercent = 0;

    public Integer getRefundAmountPercent() {
        if (this.monthRefundKpi != null && this.monthRefundKpi.compareTo(BigDecimal.ZERO) != 0) {
            this.refundAmountPercent = refundAmount.divide(this.monthRefundKpi, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
        }
        return refundAmountPercent;
    }

    @Transient
    private BigDecimal orderAmount = BigDecimal.ZERO;

    @Transient
    private BigDecimal orderReceivedAmount = BigDecimal.ZERO;

    @Transient
    private Integer orderAmountPercent = 0;

    public Integer getOrderAmountPercent() {
        if (this.monthOrderKpi != null && this.monthOrderKpi.compareTo(BigDecimal.ZERO) != 0) {
            this.orderAmountPercent = orderAmount.divide(this.monthOrderKpi, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
        }
        return orderAmountPercent;
    }

    @Transient
    private BigDecimal notPayInvoiceAmount = BigDecimal.ZERO;

    @Transient
    private BigDecimal allNeedReceiveAmount = BigDecimal.ZERO;
}

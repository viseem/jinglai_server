package cn.iocoder.yudao.module.jl.entity.statistic;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 销售数据统计缓存 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "SalesDataStatisticCache")
@Table(name = "jl_sales_data_statistic_cache")
public class SalesDataStatisticCache extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 成交金额
     */
    @Column(name = "order_amount", precision = 10, scale = 2)
    private BigDecimal orderAmount = BigDecimal.ZERO;

    /**
     * 应收金额
     */
    @Column(name = "accounts_receivable", precision = 10, scale = 2)
    private BigDecimal accountsReceivable = BigDecimal.ZERO;

    /**
     * 已开票金额
     */
    @Column(name = "invoice_amount", precision = 10, scale = 2)
    private BigDecimal invoiceAmount = BigDecimal.ZERO;

    /**
     * 回款金额
     */
    @Column(name = "payment_amount", precision = 10, scale = 2)
    private BigDecimal paymentAmount = BigDecimal.ZERO;

    /**
     * 统计时间
     */
    @Column(name = "statistic_date")
    private LocalDateTime statisticDate;
}


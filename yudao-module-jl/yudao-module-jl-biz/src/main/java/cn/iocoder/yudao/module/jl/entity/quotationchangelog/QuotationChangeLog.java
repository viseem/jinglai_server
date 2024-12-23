package cn.iocoder.yudao.module.jl.entity.quotationchangelog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import lombok.*;

import java.math.BigDecimal;
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
 * 报价变更日志 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "QuotationChangeLog")
@Table(name = "jl_quotation_change_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class QuotationChangeLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 报价id
     */
    @Column(name = "quotation_id", nullable = false )
    private Long quotationId;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    /**
     * 变更原因
     */
    @Column(name = "mark", nullable = false )
    private String mark;

    /**
     * 变更方案
     */
    @Column(name = "plan_text")
    private String planText;

    /**
     * 变更明细
     */
    @Column(name = "json_logs", nullable = false )
    private String jsonLogs;

    /**
     * 变更明细
     */
    @Column(name = "prev_amount", nullable = false )
    private BigDecimal prevAmount;

    /**
     * 变更明细
     */
    @Column(name = "current_amount", nullable = false )
    private BigDecimal currentAmount;

    /**
     * 变更明细
     */
    @Column(name = "current_discount_amount", nullable = false )
    private BigDecimal currentDiscountAmount;

    /**
     * 级联
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "creator", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}

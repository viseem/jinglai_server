package cn.iocoder.yudao.module.jl.entity.projectfundchangelog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 款项计划变更日志 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectFundChangeLog")
@Table(name = "jl_project_fund_change_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectFundChangeLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 款项计划
     */
    @Column(name = "project_fund_id", nullable = false )
    private Long projectFundId;

    /**
     * 原状态
     */
    @Column(name = "origin_status", nullable = false )
    private String originStatus;

    /**
     * 状态
     */
    @Column(name = "status", nullable = false )
    private String status;

    /**
     * 备注
     */
    @Column(name = "mark", nullable = false )
    private String mark;

    /**
     * 销售id
     */
    @Column(name = "sales_id", nullable = false )
    private Long salesId;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 合同id
     */
    @Column(name = "contract_id", nullable = false )
    private Long contractId;

    /**
     * 款项名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 款项金额
     */
    @Column(name = "price", nullable = false )
    private Long price;

    /**
     * 款项应收日期
     */
    @Column(name = "deadline")
    private LocalDateTime deadline;

    /**
     * 变更类型 默认1：状态变更
     */
    @Column(name = "change_type", nullable = false )
    private String changeType;

    /**
     * 实际到款日期
     */
    @Column(name = "actual_payment_time", nullable = false )
    private LocalDateTime actualPaymentTime;


    /**
     * 实际支付金额
     */
    @Column(name = "actual_payment_amount", nullable = false )
    private Integer actualPaymentAmount;

}

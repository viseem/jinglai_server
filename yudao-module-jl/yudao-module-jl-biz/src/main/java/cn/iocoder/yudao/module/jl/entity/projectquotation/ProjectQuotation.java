package cn.iocoder.yudao.module.jl.entity.projectquotation;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目报价 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectQuotation")
@Table(name = "jl_project_quotation")
@SQLDelete(sql = "UPDATE jl_project_quotation SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class ProjectQuotation extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 版本号
     */
    @Column(name = "code", nullable = false )
    private String code;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 方案  不能通过update和save修改
     */
    @Column(name = "plan_text",insertable = false,updatable = false)
    private String planText;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    @Column(name = "discount", nullable = false )
    private Integer discount;

    @Column(name = "origin_price", nullable = false )
    private BigDecimal originPrice;

    @Column(name = "reduce_price", nullable = false )
    private BigDecimal reducePrice;

    /**
     * 实验名称
     */
    @Column(name = "exp_name")
    private String expName;

    /**
     * 实验目的
     */
    @Column(name = "exp_purpose")
    private String expPurpose;

    /**
     * 操作方法
     */
    @Column(name = "operate_method")
    private String operateMethod;

    /**
     * 注意事项
     */
    @Column(name = "attention")
    private String attention;

    /**
     * 风险提示
     */
    @Column(name = "risk_tips")
    private String riskTips;

    /**
     * 交付标准
     */
    @Column(name = "delivery_standard")
    private String deliveryStandard;
}

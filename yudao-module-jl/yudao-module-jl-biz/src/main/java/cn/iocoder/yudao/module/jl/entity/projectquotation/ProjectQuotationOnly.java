package cn.iocoder.yudao.module.jl.entity.projectquotation;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.crm.SalesleadOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 项目报价 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectQuotationOnly")
@Table(name = "jl_project_quotation")
@SQLDelete(sql = "UPDATE jl_project_quotation SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class ProjectQuotationOnly extends BaseEntity {

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

    @Column(name = "current_discount", nullable = false )
    private Integer currentDiscount;

    @Column(name = "origin_price", nullable = false )
    private BigDecimal originPrice;

    @Column(name = "reduce_price", nullable = false )
    private BigDecimal reducePrice;

    @Column(name = "current_reduce_price", nullable = false )
    private BigDecimal currentReducePrice;

    @Column(name = "result_price", nullable = false )
    private BigDecimal resultPrice;

    @Column(name = "supply_discount", nullable = false )
    private BigDecimal supplyDiscount;

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

    /**
     * 报价审批状态
     */
    @Column(name = "audit_status")
    private String auditStatus;

    /**
     * 报价审批备注
     */
    @Column(name = "audit_mark")
    private String auditMark;

    /**
     * 报价审批id
     */
    @Column(name = "audit_process_id")
    private String auditProcessId;

    /**
     * 商机id
     */
    @Column(name = "saleslead_id")
    private Long salesleadId;


}

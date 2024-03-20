package cn.iocoder.yudao.module.jl.entity.contractfundlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 合同收款记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ContractFundLogOnly")
@Table(name = "jl_contract_fund_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContractFundLogOnly extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 核验金额
     */
    @Column(name = "price", nullable = false )
    private BigDecimal price;

    /*
     * 实际金额
     * */
    @Column(name = "received_price", nullable = false )
    private BigDecimal receivedPrice;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 凭证地址
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;

    /**
     * 凭证
     */
    @Column(name = "file_name", nullable = false )
    private String fileName;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 支付日期
     */
    @Column(name = "paid_time", nullable = false )
    private LocalDateTime paidTime;

    /**
     * 付款方
     */
    @Column(name = "payer")
    private String payer;

    /**
     * 收款方
     */
    @Column(name = "payee")
    private String payee;

    /**
     * 合同id
     */
    @Column(name = "contract_id", nullable = false )
    private Long contractId;

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

    /*
    * 销售id
    * */
    @Column(name = "sales_id", nullable = false )
    private Long salesId;

    /*
     * 销售id
     * */
    @Column(name = "audit_id", nullable = false )
    private Long auditId;

    /*
     * 状态
     * */
    @Column(name = "status", nullable = false )
    private String status;
}

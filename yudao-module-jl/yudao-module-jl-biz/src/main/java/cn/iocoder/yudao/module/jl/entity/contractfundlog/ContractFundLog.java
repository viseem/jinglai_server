package cn.iocoder.yudao.module.jl.entity.contractfundlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
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
import java.time.LocalDateTime;

/**
 * 合同收款记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ContractFundLog")
@Table(name = "jl_contract_fund_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContractFundLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 金额
     */
    @Column(name = "price", nullable = false )
    private Long price;

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


    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "contract_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectConstractOnly contract;

}
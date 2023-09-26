package cn.iocoder.yudao.module.jl.entity.projectfundlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFundOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 项目款项 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectFundLog")
@Table(name = "jl_project_fund_log")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectFundLog extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 收款金额
     */
    @Column(name = "price", nullable = false )
    private Integer price;

    /**
     * 合同 id
     */
    @Column(name = "contract_id", nullable = false )
    private Long contractId;


    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "contract_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectConstractOnly contract;

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 客户 id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

    /**
     * 支付凭证上传地址
     */
    @Column(name = "receipt_url", nullable = false )
    private String receiptUrl;

    /**
     * 支付时间
     */
    @Column(name = "paid_time")
    private String paidTime;

    /**
     * 支付凭证文件名称
     */
    @Column(name = "receipt_name", nullable = false )
    private String receiptName;

    /**
     * 付款方
     */
    @Column(name = "payer", nullable = false )
    private String payer;

    /**
     * 收款方
     */
    @Column(name = "payee", nullable = false )
    private String payee;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 项目款项主表id
     */
    @Column(name = "project_fund_id", nullable = false )
    private Long projectFundId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_fund_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectFundOnly fund;

    @ManyToOne
    @JoinColumn(name="project_fund_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonBackReference
    private ProjectFund projectFund;

}

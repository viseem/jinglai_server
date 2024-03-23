package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.contract.ContractApproval;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 项目合同 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectConstract")
@Table(name = "jl_project_constract")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectConstract extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "sales_id", insertable = false, updatable = false)
    private User sales;

    /**
     * 项目 id
     */
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

/*    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "sales_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User sales;*/

    /**
     * 合同名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 公司主体名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    @Column(name = "customer_id")
    private Long customerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerSimple customer;

    /**
     * 合同文件 URL
     */
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    /**
     * 合同附件
     */
    @Column(name = "json_file", nullable = false)
    private String jsonFile;

    /**
     * 盖章合同文件 URL
     */
    @Column(name = "stamp_file_url", nullable = false)
    private String stampFileUrl;

    /**
     * 盖章合同文件 NAME
     */
    @Column(name = "stamp_file_name", nullable = false)
    private String stampFileName;

    /**
     * 合同状态：起效、失效、其它
     */
    @Column(name = "status")
    private String status;

    @Column(name = "pay_status")
    private String payStatus;

    /**
     * 合同业务类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 合同类型
     */
    @Column(name = "contract_type")
    private String contractType;

    /**
     * 合同应收金额
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 合同账面金额
     */
    @Column(name = "paper_price")
    private BigDecimal paperPrice;

    /**
     * 结算金额
     */
    @Column(name = "real_price")
    private BigDecimal realPrice; // 弃用

    /**
     * 已收金额
     */
    @Column(name = "received_price")
    private BigDecimal receivedPrice;

    /**
     * 已开票金额
     */
    @Column(name = "invoiced_price")
    private BigDecimal invoicedPrice;

    /**
     * 签订销售人员
     */
    @Column(name = "sales_id")
    private Long salesId;

    /**
     * 合同编号
     */
    @Column(name = "sn")
    private String sn;

    /**
     * 合同文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 合同关联的文档id
     */
    @Column(name = "project_document_id")
    private Long projectDocumentId;


    /**
     * 是否出库
     */
    @Column(name = "is_outed")
    private Integer isOuted;

    /**
     * 签订日期
     */
    @Column(name = "signed_time")
    private LocalDateTime signedTime;

    /**
     * 查询审批列表
     */
/*    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "contract_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy("createTime DESC")
    private List<ContractApproval> approvalList = new ArrayList<>();*/

    @Transient
    private ContractApproval latestApproval;


    /**
     * 查询回款计划列表
     */
/*    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "contract_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectFund> funds;*/


    /**
     * 查询回款日志列表
     */
/*    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "contract_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProjectFundLog> fundLogs;*/

}

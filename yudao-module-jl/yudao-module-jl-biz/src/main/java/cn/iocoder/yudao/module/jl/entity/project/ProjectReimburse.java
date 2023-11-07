package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目报销 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectReimburse")
@Table(name = "jl_project_reimburse")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectReimburse extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 报销类型：物流成本、差旅费、其它
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 项目的实验名目id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    @Column(name = "schedule_id")
    private Long scheduleId;

    /**
     * 报销内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 凭证名字
     */
    @Column(name = "proof_name", nullable = false )
    private String proofName;

    /**
     * 凭证地址
     */
    @Column(name = "proof_url", nullable = false )
    private String proofUrl;

    /**
     * 报销金额
     */
    @Column(name = "price", nullable = false )
    private Integer price;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;


    @Column(name = "paid_price")
    private BigDecimal paidPrice;
    /**
     * 查询款项列表
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @Where(clause = "type = '2'")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<FinancePayment> paymentList = new ArrayList<>();

}

package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;

/**
 * 项目采购单打款 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProcurementPayment")
@Table(name = "jl_project_procurement_payment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProcurementPayment extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * JPA 级联出 user
     */
    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

    /**
     * 采购单id
     */
    @Column(name = "procurement_id", nullable = false)
    private Long procurementId;

    /**
     * 打款时间
     */
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    /**
     * 打款金额
     */
    @Column(name = "amount")
    private Long amount;

    /**
     * 供货商 id
     */
    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 凭证
     */
    @Column(name = "proof_name")
    private String proofName;

    /**
     * 凭证地址
     */
    @Column(name = "proof_url")
    private String proofUrl;

    @OneToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Supplier supplier;

    @OneToOne
    @JoinColumn(name = "procurement_id", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Procurement procurement;
}

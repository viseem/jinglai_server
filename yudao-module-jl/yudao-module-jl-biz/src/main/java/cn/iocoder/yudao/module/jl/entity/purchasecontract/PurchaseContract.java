package cn.iocoder.yudao.module.jl.entity.purchasecontract;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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
import java.math.BigDecimal;

/**
 * 购销合同 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PurchaseContract")
@Table(name = "jl_purchase_contract")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PurchaseContract extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 供应商id
     */
    @Column(name = "supplier_id", nullable = false )
    private Integer supplierId;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 状态
     */
    @Column(name = "status", nullable = false )
    private String status;

    /**
     * 总价
     */
    @Column(name = "amount", nullable = false )
    private BigDecimal amount;

    /*
     * 级联附件
     * */
    @OneToMany(fetch = FetchType.EAGER)
    @Where(clause = "type = 'PROCUREMENT_PURCHASE_CONTRACT'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "ref_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CommonAttachment> attachmentList = new ArrayList<>();

}

package cn.iocoder.yudao.module.jl.entity.purchasecontract;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购销合同 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PurchaseContractOnly")
@Table(name = "jl_purchase_contract")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PurchaseContractOnly extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 实验室id
     */
    @Column(name = "lab_id", nullable = false )
    private Long labId;

    /**
     * 采购类型 项目 实验室 行政
     */
    @Column(name = "procurement_type", nullable = false )
    private Integer procurementType;

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

    /**
     * 流程实例id
     */
    @Column(name = "process_instance_id", nullable = false )
    private String processInstanceId;



}

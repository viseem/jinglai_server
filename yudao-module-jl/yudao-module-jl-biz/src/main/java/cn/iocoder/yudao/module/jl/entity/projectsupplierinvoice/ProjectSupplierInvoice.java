package cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
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

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 采购供应商发票 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectSupplierInvoice")
@Table(name = "jl_project_supplier_invoice")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectSupplierInvoice extends BaseEntity {

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
    private BigDecimal price;

    /**
     * 凭证
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 凭证
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 购销合同id
     */
    @Column(name = "purchaseContractId", nullable = false )
    private Long purchaseContractId;

    /**
     * 项目id 已弃用
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 采购单id 已弃用
     */
    @Column(name = "procurement_id", nullable = false )
    private Long procurementId;

    /**
     * 供应商id
     */
    @Column(name = "supplier_id", nullable = false )
    private Long supplierId;

    /**
     * 开票日期
     */
    @Column(name = "date")
    private LocalDateTime date;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;


}

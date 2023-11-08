package cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
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
    private Long price;

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
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectSimple project;

}

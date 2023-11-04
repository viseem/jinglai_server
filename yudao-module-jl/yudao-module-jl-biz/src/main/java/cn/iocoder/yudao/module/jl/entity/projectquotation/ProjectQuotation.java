package cn.iocoder.yudao.module.jl.entity.projectquotation;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目报价 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectQuotation")
@Table(name = "jl_project_quotation")
@SQLDelete(sql = "UPDATE jl_project_quotation SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class ProjectQuotation extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 版本号
     */
    @Column(name = "code", nullable = false )
    private String code;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 方案  不能通过update和save修改
     */
    @Column(name = "plan_text",insertable = false,updatable = false)
    private String planText;

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

}

package cn.iocoder.yudao.module.jl.entity.projectoutlog;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * example 空 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectOutLog")
@Table(name = "jl_project_out_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectOutLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 报价id
     */
    @Column(name = "quotation_id", nullable = false )
    private Long quotationId;

    /**
     * 客户id
     */
    @Column(name = "customer_id", nullable = false )
    private Long customerId;


    /**
     *
     */
    @Column(name = "step1_json")
    private String step1Json;

    /**
     *
     */
    @Column(name = "step2_json")
    private String step2Json;

    /**
     *
     */
    @Column(name = "step3_json")
    private String step3Json;

    /**
     *
     */
    @Column(name = "step4_json")
    private String step4Json;
}

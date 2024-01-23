package cn.iocoder.yudao.module.jl.entity.auditconfig;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 审批配置表  Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AuditConfig")
@Table(name = "jl_audit_config")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AuditConfig extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 路由
     */
    @Column(name = "route", nullable = false )
    private String route;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 是否需要审批
     */
    @Column(name = "need_audit", nullable = false )
    private Boolean needAudit;


    /**
     * 说明
     */
    @Column(name = "mark", nullable = false )
    private String mark;

}

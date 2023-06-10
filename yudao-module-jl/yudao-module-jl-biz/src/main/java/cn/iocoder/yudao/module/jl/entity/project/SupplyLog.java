package cn.iocoder.yudao.module.jl.entity.project;

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
 * 项目物资变更日志 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplyLog")
@Table(name = "jl_project_supply_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplyLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 相关的id，采购、寄来、取货
     */
    @Column(name = "ref_id", nullable = false )
    private Long refId;

    /**
     * 类型：采购、寄来、取货
     */
    @Column(name = "type", nullable = false )
    private Long type;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 备注描述
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 变更描述
     */
    @Column(name = "log")
    private String log;

}

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
 * 库管项目物资库存变更日志附件 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SupplyLogAttachment")
@Table(name = "jl_project_supply_log_attachment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplyLogAttachment extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目物资日志表id
     */
    @Column(name = "project_supply_log_id", nullable = false )
    private Long projectSupplyLogId;

    /**
     * 附件名称
     */
    @Column(name = "file_name", nullable = false )
    private String fileName;

    /**
     * 附件地址
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;

}

package cn.iocoder.yudao.module.jl.entity.inventory;

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
 * 库管操作附件记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InventoryOptAttachment")
@Table(name = "jl_inventory_opt_attachment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InventoryOptAttachment extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 项目 id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 类型，采购，寄送，自取
     */
    @Column(name = "type")
    private String type;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 采购，寄送，自取的 id
     */
    @Column(name = "ref_id")
    private Long refId;

    /**
     * 采购，寄送，自取的子元素 id
     */
    @Column(name = "ref_item_id")
    private Long refItemId;

    /**
     * 名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 地址
     */
    @Column(name = "file_url")
    private String fileUrl;

}

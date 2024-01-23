package cn.iocoder.yudao.module.jl.entity.commonattachment;

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
 * 通用附件 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CommonAttachment")
@Table(name = "jl_common_attachment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommonAttachment extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * id
     */
    @Column(name = "ref_id", nullable = false )
    private Long refId;

    /**
     * 文件名称
     */
    @Column(name = "file_name", nullable = false )
    private String fileName;

    /**
     * 文件地址
     */
    @Column(name = "file_url", nullable = false )
    private String fileUrl;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

}

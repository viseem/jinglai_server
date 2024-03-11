package cn.iocoder.yudao.module.jl.entity.commonoperatelog;

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
 * 通用操作记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CommonOperateLog")
@Table(name = "jl_common_operate_log")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommonOperateLog extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 旧内容
     */
    @Column(name = "old_content")
    private String oldContent;

    /**
     * 新内容
     */
    @Column(name = "new_content")
    private String newContent;

    /**
     * 父类型
     */
    @Column(name = "type", nullable = false )
    private String type;

    /**
     * 子类型
     */
    @Column(name = "sub_type")
    private String subType;

    /**
     * 事件类型
     */
    @Column(name = "event_type")
    private String eventType;

    /**
     * 父类型关联id
     */
    @Column(name = "ref_id")
    private Long refId;

    /**
     * 子类型关联id
     */
    @Column(name = "sub_ref_id")
    private Long subRefId;

}

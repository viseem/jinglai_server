package cn.iocoder.yudao.module.jl.entity.devicecate;

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
 * 设备分类 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "DeviceCate")
@Table(name = "jl_device_cate")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DeviceCate extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 标签
     */
    @Column(name = "tag_ids")
    private String tagIds;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 详细介绍
     */
    @Column(name = "content")
    private String content;

    @Transient
    private List<DeviceCate> childList;

}

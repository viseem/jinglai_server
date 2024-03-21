package cn.iocoder.yudao.module.jl.entity.laboratory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 实验名目的操作SOP Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CategorySop")
@Table(name = "jl_laboratory_category_sop")
public class CategorySop extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 实验名目 id
     */
    @Column(name = "category_id", nullable = false )
    private Long categoryId;

    /**
     * 操作步骤的名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 操作步骤的内容
     */
    @Column(name = "content", nullable = false )
    private String content;

    /**
     * 步骤序号
     */
    @Column(name = "step", nullable = false )
    private Integer step;

    /**
     * 注意事项
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 依赖项(json数组多个)
     */
    @Column(name = "depend_ids")
    private String dependIds;

}

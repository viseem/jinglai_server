package cn.iocoder.yudao.module.jl.entity.laboratory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 实验名目 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CategoryOnly")
@Table(name = "jl_laboratory_category")
public class CategoryOnly extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 名字
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 技术难度
     */
    @Column(name = "difficulty_level")
    private String difficultyLevel;

    /**
     * 重要备注说明
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 实验室id
     */
    @Column(name = "lab_id", nullable = false)
    private Long labId;

    /**
     * 原理
     */
    @Column(name = "principle", nullable = false)
    private String principle;

    /**
     * 目的
     */
    @Column(name = "purpose", nullable = false)
    private String purpose;

    /**
     * 准备
     */
    @Column(name = "preparation", nullable = false)
    private String preparation;

    /**
     * 注意事项
     */
    @Column(name = "caution", nullable = false)
    private String caution;

    /**
     * tag ids
     */
    @Column(name = "tag_ids")
    private String tagIds;

    /**
     * 历史操作次数
     */
    @Column(name = "action_count")
    private Integer actionCount = 0;


}

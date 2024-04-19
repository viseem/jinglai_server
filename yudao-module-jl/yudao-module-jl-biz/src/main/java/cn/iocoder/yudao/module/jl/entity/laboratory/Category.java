package cn.iocoder.yudao.module.jl.entity.laboratory;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 实验名目 Entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Category")
@Table(name = "jl_laboratory_category")
public class Category extends BaseEntity {

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
     * JPA 级联出 open
     */
/*    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "lab_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LaboratoryLab lab;*/

    /**
     * 历史操作次数
     */
    @Column(name = "action_count")
    private Integer actionCount = 0;

    /**
     * 实验名目的擅长人员
     */
/*    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinTable(
            name = "jl_laboratory_category_skilluser",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> skillUsers = new HashSet<>();*/

    /**
     * charge list
     */
/*    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CategoryChargeitem> chargeList = new ArrayList<>();*/

    /**
     * supply list
     */
/*    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CategorySupply> supplyList = new ArrayList<>();*/

    /**
     * attachment list
     */

    /**
     * 名字
     */
    @Column(name = "step", nullable = false)
    private String step;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CategoryReference> attachmentList = new ArrayList<>();

    /**
     * sop list
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CategorySop> sopList = new ArrayList<>();

}

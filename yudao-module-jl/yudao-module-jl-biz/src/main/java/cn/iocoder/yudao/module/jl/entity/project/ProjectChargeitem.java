package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目中的实验名目的收费项 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectChargeitem")
@Table(name = "jl_project_chargeitem")
@SQLDelete(sql = "UPDATE jl_project_chargeitem SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class ProjectChargeitem extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 实验名目 id
     */
    @Column(name = "project_category_id", nullable = false )
    private Long projectCategoryId;

    /**
     * 原始的实验名目 id
     */
    @Column(name = "category_id", nullable = false )
    private Long categoryId;

    /**
     * 物资 id
     */
    @Column(name = "charge_item_id", nullable = false )
    private Long chargeItemId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false )
    private String name;

    /**
     * 规则/单位
     */
    @Column(name = "fee_standard", nullable = false )
    private String feeStandard;
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;
    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;
    @Column(name = "final_usage_num")
    private Integer finalUsageNum;

    @Column(name = "is_append")
    private Integer isAppend;

    @Column(name = "sort")
    private Integer sort;
    /**
     * 单价
     */
    @Column(name = "unit_fee", nullable = false )
    private Integer unitFee;

    @Column(name = "spec", nullable = false )
    private String spec;

    /**
     * 单量
     */
    @Column(name = "unit_amount", nullable = false )
    private Integer unitAmount;

    /**
     * 成本价
     */
    @Column(name = "buy_price")
    private Integer buyPrice = 0;

    /**
     * 数量
     */
    @Column(name = "quantity", nullable = false )
    private Integer quantity;

    /**
     * 折扣
     */
    @Column(name = "discount", nullable = false )
    private Integer discount;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "project_category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectCategoryOnly category;
}

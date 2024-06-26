package cn.iocoder.yudao.module.jl.entity.commontask;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 通用任务 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CommonTask")
@Table(name = "jl_common_task")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CommonTask extends BaseEntity {

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
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 状态
     */
    @Column(name = "status", nullable = false )
    private Integer status;

    /**
     * 开始时间
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * 负责人
     */
    @Column(name = "user_id", nullable = false )
    private Long userId;

    /**
     * 负责人昵称
     */
    @Column(name = "user_nickname")
    private String userNickname;

    /**
     * 关注人
     */
    @Column(name = "focus_ids")
    private String focusIds;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 收费项id
     */
    @Column(name = "chargeitem_id")
    private Long chargeitemId;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * categoryid
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 报价id
     */
    @Column(name = "quotation_id")
    private Long quotationId;

    /**
     * 项目
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 客户
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 收费项
     */
    @Column(name = "chargeitem_name")
    private String chargeitemName;


    /**
     * 实验目录
     */
    @Column(name = "project_category_name")
    private String projectCategoryName;

    /**
     * 类型
     */
    @Column(name = "type", nullable = false )
    private Integer type;

    /**
     * 创建类型
     */
    @Column(name = "create_type", nullable = false )
    private Integer createType;

    /**
     * 紧急程度
     */
    @Column(name = "level", nullable = false )
    private Integer level;

    /**
     * 指派人id
     */
    @Column(name = "assign_user_id", nullable = false )
    private Long assignUserId;

    /**
     * 指派人
     */
    @Column(name = "assign_user_name", nullable = false )
    private String assignUserName;

    /**
     * 实验室id
     */
    @Column(name = "lab_ids")
    private String labIds;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Long parentId;

    @Transient
    private List<TaskProduct> productList;

}

package cn.iocoder.yudao.module.jl.entity.projectservice;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 项目售后 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectService")
@Table(name = "jl_project_service")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectService extends BaseEntity {

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
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 实验名目 id
     */
    @Column(name = "project_category_id")
    private Long projectCategoryId;

    /**
     * 内部人员 id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 客户 id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 反馈的内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 处理结果
     */
    @Column(name = "result")
    private String result;

    /**
     * 处理人
     */
    @Column(name = "result_user_id")
    private Long resultUserId;

    /**
     * 处理时间
     */
    @Column(name = "result_time")
    private LocalDateTime resultTime;

}

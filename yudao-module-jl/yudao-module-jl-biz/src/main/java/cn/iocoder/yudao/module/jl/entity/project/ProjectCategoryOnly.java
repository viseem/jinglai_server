package cn.iocoder.yudao.module.jl.entity.project;

import cn.iocoder.yudao.module.jl.entity.BaseEntity;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.module.jl.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 项目的实验名目 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectCategoryOnly")
@Table(name = "jl_project_category")
@Where(clause = "deleted = false")
public class ProjectCategoryOnly extends BaseEntity {

    /**
     * 岗位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 报价 id
     */
    @Column(name = "quote_id")
    private Long quoteId;

    /**
     * 安排单 id
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * 安排单 id
     */
    @Column(name = "schedule_id")
    private Long scheduleId;

    /**
     * 类型，报价/安排单
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 所属实验室id
     */
    @Column(name = "lab_id")
    private Long labId;


    /**
     * 名目的实验类型，动物/细胞/分子等
     */
    @Column(name = "category_type")
    private String categoryType;

    /**
     * 实验名目库的名目 id
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;


    /**
     * 实验人员
     */
    @Column(name = "operator_id")
    private Long operatorId;

    /**
     * 客户需求
     */
    @Column(name = "demand")
    private String demand;

    /**
     * 干扰项
     */
    @Column(name = "interference")
    private String interference;

    /**
     * 依赖项(json数组多个)
     */
    @Column(name = "depend_ids")
    private String dependIds;

    /**
     * 实验名目名字
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 备注
     */
    @Column(name = "mark")
    private String mark;

    /**
     * 当前实验的状态，未开展、开展中、数据审核、已完成
     */
    @Column(name = "stage")
    private String stage;

}

package cn.iocoder.yudao.module.jl.entity.project;

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
 * 项目的状态变更记录 Entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProjectApproval")
@Table(name = "jl_project_approval")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectApproval extends BaseEntity {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;

    /**
     * 申请变更的状态：提前开展、项目开展、暂停、中止、退单、提前出库、出库、售后
     */
    @Column(name = "stage", nullable = false )
    private String stage;

    /**
     * 申请的备注
     */
    @Column(name = "stage_mark")
    private String stageMark;

    /**
     * 审批人id
     */
    @Column(name = "approval_user_id")
    private Long approvalUserId;

    /**
     * 审批备注
     */
    @Column(name = "approval_mark")
    private String approvalMark;

    /**
     * 审批状态：等待审批、批准、拒绝
     */
    @Column(name = "approval_stage", nullable = false )
    private String approvalStage;

    /**
     * 项目的id
     */
    @Column(name = "project_id", nullable = false )
    private Long projectId;

    /**
     * 安排单id
     */
    @Column(name = "schedule_id", nullable = false )
    private Long scheduleId;

}
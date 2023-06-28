package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;

/**
 * 项目实验名目的状态变更审批 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectCategoryApprovalBaseVO {

    @Schema(description = "申请变更的状态：开始、暂停、数据审批", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请变更的状态：开始、暂停、数据审批不能为空")
    private String stage;

    @Schema(description = "申请的备注")
    private String stageMark;

    @Schema(description = "审批人id", example = "26290")
    private Long approvalUserId;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批状态：等待审批、已审批")
    private String approvalStage;

    @Schema(description = "项目的实验名目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24706")
    @NotNull(message = "项目的实验名目id不能为空")
    private Long projectCategoryId;

    private ProjectCategoryOnly projectCategory;

    @Schema(description = "安排单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23230")
    @NotNull(message = "安排单id不能为空")
    private Long scheduleId;


}

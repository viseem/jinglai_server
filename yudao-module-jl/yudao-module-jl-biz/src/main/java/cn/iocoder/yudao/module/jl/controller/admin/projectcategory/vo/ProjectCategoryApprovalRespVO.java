package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 项目实验名目的状态变更审批 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryApprovalRespVO extends ProjectCategoryApprovalBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28209")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "变更前状态")
    private String originStage;

    @Schema(description = "申请变更状态")
    private String stage;

    @Schema(description = "申请备注")
    private String stageMark;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批状态：等待审批、已审批")
    private String approvalStage;

    @Schema(description = "申请人")
    private User user;

    private ProjectCategoryOnly projectCategory;

    private User approvalUser;

    private Approval approval;

    private String processInstanceId;
}

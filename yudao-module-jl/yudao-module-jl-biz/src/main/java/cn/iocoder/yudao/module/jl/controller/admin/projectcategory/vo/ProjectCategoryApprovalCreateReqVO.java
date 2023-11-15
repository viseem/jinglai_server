package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import cn.iocoder.yudao.module.jl.entity.user.User;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目实验名目的状态变更审批创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryApprovalCreateReqVO extends ProjectCategoryApprovalBaseVO {

    private Boolean needAudit;

    @Schema(description = "变更前状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更前状态")
    private String originStage;

    @Schema(description = "申请变更的状态：开始、暂停、数据审批", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请变更的状态：开始、暂停、数据审批不能为空")
    private String stage;

    @Schema(description = "申请的备注")
    private String stageMark;
    @Schema(description = "项目的实验名目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24706")
    @NotNull(message = "项目的实验名目id不能为空")
    private Long projectCategoryId;

    private String approvalStage;

    private List<User> userList;

}

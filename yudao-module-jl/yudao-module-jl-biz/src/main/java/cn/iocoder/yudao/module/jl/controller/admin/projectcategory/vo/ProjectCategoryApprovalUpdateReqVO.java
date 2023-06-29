package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目实验名目的状态变更审批更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryApprovalUpdateReqVO extends ProjectCategoryApprovalBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28209")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "申请变更的状态：开始、暂停、数据审批", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请变更的状态：开始、暂停、数据审批不能为空")
    private String stage;

    @Schema(description = "申请的备注")
    private String stageMark;
    @Schema(description = "项目的实验名目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24706")
    @NotNull(message = "项目的实验名目id不能为空")
    private Long projectCategoryId;


    @Schema(description = "安排单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23230")
    @NotNull(message = "安排单id不能为空")
    private Long scheduleId;

    @Schema(description = "审批人id", example = "26290")
    private Long approvalUserId;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批状态：等待审批、已审批")
    private String approvalStage = "0";

}

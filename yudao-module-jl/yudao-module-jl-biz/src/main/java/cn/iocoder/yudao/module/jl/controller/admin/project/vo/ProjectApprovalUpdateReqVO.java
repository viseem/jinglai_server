package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目的状态变更记录更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectApprovalUpdateReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25843")
    @NotNull(message = "ID不能为空")
    private Long id;


    @Schema(description = "审批状态：等待审批、批准、拒绝", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审批状态：等待审批、批准、拒绝不能为空")
    private String approvalStage;

}

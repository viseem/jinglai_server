package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 审批流程更新 Request VO")
@Data
@ToString(callSuper = true)
public class ApprovalProgressUpdateReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25583")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "refID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25583")
    @NotNull(message = "refID不能为空")
    private Long refId;

    @Schema(description = "审批意见", requiredMode = Schema.RequiredMode.REQUIRED, example = "31967")
    @NotNull(message = "审批意见不能为空")
    private String approvalMark;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "31967")
    @NotNull(message = "审批状态不能为空")
    private String approvalStage;

}

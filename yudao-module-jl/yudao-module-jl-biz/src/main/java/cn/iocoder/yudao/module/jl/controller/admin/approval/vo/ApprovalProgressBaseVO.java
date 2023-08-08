package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 审批流程 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ApprovalProgressBaseVO {

    @Schema(description = "审批id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31967")
    @NotNull(message = "审批id不能为空")
    private Long approvalId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14090")
    @NotNull(message = "用户id不能为空")
    private Long toUserId;

    @Schema(description = "审批状态")
    private String approvalStage;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批类型：抄送，审批", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "审批类型：抄送，审批不能为空")
    private String type;
    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型不能为空")
    private String approvalType;
}

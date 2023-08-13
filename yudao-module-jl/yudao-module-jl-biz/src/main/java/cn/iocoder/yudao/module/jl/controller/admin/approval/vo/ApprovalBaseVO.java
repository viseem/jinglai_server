package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 审批 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ApprovalBaseVO {

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容不能为空")
    private String content;

    @Schema(description = "内容详情id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6115")
    @NotNull(message = "内容详情id不能为空")
    private Long refId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "当前审批人")
    private String currentApprovalUser;

    @Schema(description = "当前审批状态")
    private String currentApprovalStage;

}

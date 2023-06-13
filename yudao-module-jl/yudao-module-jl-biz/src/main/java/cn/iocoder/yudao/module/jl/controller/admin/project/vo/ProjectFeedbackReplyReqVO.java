package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 处理反馈 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectFeedbackReplyReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7914")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "处理方式")
    @NotNull(message = "处理方式不能为空")
    private String result;

    @Schema(description = "处理人ID", hidden = true)
    private Long resultUserId;

    @Schema(description = "状态", hidden = true)
    private String status;
}

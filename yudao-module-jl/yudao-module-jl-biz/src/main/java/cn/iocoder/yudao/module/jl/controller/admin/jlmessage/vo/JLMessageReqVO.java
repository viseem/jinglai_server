package cn.iocoder.yudao.module.jl.controller.admin.jlmessage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 消息通知")
@Data
@ToString(callSuper = true)
public class JLMessageReqVO {

    @Schema(description = "processInstanceId")
    private String processInstanceId;

    @Schema(description = "refid")
    private Long refId;

    @Schema(description = "sendToUserId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "接收人不能为空")
    private Long sendToUserId;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "标题不能为空")
    private String title;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "备注")
    private String msgType;

    // 有的消息需要跳转到项目，需要把项目名称传过去
    @Schema(description = "项目名称")
    private String projectName;
}

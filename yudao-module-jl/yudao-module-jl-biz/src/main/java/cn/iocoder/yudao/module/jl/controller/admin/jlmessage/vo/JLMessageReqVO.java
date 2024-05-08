package cn.iocoder.yudao.module.jl.controller.admin.jlmessage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Schema(description = "管理后台 - 消息通知")
@Data
@ToString(callSuper = true)
public class JLMessageReqVO {

    @Schema(description = "processInstanceId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String processInstanceId;

    @Schema(description = "sendToUserId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long sendToUserId;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String title;

    @Schema(description = "备注")
    private String mark;
}

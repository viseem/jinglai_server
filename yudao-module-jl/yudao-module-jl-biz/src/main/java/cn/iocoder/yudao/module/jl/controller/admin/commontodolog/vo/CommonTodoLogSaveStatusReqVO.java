package cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 通用TODO记录更新 Request VO")
@Data
@ToString(callSuper = true)
public class CommonTodoLogSaveStatusReqVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "refId", requiredMode = Schema.RequiredMode.REQUIRED, example = "11333")
    private Long refId;

    @Schema(description = "todoId", requiredMode = Schema.RequiredMode.REQUIRED, example = "11333")
    private Long todoId;

    @Schema(description = "todo状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;

    @Schema(description = "todo类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String type;

}

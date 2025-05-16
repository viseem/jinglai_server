package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 通用任务创建 Request VO")
@Data
@ToString(callSuper = true)
public class CommonTaskTransferManagerReqVO {

    @Schema(description = "fromUserId", requiredMode = Schema.RequiredMode.REQUIRED, example = "17096")
    @NotNull(message = "fromUserId不能为空")
    private Long fromUserId;
    @Schema(description = "toUserId", requiredMode = Schema.RequiredMode.REQUIRED, example = "17096")
    @NotNull(message = "toUserId不能为空")
    private Long toUserId;
    @Schema(description = "projectId", requiredMode = Schema.RequiredMode.REQUIRED, example = "17096")
    @NotNull(message = "projectId不能为空")
    private Long projectId;

}

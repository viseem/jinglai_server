package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 审批更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApprovalUpdateReqVO extends ApprovalBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7113")
    @NotNull(message = "ID不能为空")
    private Long id;

}

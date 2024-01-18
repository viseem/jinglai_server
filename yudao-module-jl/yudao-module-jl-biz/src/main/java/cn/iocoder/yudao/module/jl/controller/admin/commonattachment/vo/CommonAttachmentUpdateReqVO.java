package cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 通用附件更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommonAttachmentUpdateReqVO extends CommonAttachmentBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24278")
    @NotNull(message = "ID不能为空")
    private Long id;

}

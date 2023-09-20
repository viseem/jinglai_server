package cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 审批配置表 更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AuditConfigUpdateReqVO extends AuditConfigBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24959")
    @NotNull(message = "ID不能为空")
    private Long id;

}

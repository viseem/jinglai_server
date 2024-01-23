package cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 审批配置表  Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AuditConfigBaseVO {

    @Schema(description = "路由", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "路由不能为空")
    private String route;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "是否需要审批", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否需要审批不能为空")
    private Boolean needAudit;

    @Schema(description = "备注")
    private String mark;

}

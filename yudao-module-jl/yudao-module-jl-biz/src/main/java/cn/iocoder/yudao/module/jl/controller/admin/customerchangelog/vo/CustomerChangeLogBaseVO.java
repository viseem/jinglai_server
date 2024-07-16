package cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 客户变更日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CustomerChangeLogBaseVO {

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31576")
    @NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "转给谁", example = "28365")
    private Long toOwnerId;

    @Schema(description = "来自谁", example = "15951")
    private Long fromOwnerId;

}

package cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目人员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectPersonBaseVO {

    @Schema(description = "人员", requiredMode = Schema.RequiredMode.REQUIRED, example = "7732")
    @NotNull(message = "人员不能为空")
    private Long userId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13810")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

}

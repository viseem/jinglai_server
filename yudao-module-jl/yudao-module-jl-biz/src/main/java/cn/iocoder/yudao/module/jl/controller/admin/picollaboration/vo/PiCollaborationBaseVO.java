package cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * PI组协作 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PiCollaborationBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "协作的事项id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2043")
    @NotNull(message = "协作的事项id不能为空")
    private Long targetId;

    @Schema(description = "协作的事项类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "协作的事项类型不能为空")
    private String targetType;

    @Schema(description = "备注")
    private String mark;

}

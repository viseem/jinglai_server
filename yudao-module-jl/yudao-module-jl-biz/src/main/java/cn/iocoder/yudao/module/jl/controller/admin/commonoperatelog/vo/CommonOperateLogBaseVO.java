package cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 通用操作记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CommonOperateLogBaseVO {

    @Schema(description = "旧内容")
    private String oldContent;

    @Schema(description = "新内容")
    private String newContent;

    @Schema(description = "父类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "父类型不能为空")
    private String type;

    @Schema(description = "子类型", example = "2")
    private String subType;

    @Schema(description = "事件类型", example = "2")
    private String eventType;

    @Schema(description = "父类型关联id", example = "22355")
    private Long refId;

    @Schema(description = "子类型关联id", example = "29358")
    private Long subRefId;

}

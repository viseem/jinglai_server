package cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 通用附件 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CommonAttachmentBaseVO {

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10568")
    @NotNull(message = "id不能为空")
    private Integer refId;

    @Schema(description = "文件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "文件名称不能为空")
    private String fileName;

    @Schema(description = "文件地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "文件地址不能为空")
    private String fileUrl;

    @Schema(description = "备注")
    private String mark;

}

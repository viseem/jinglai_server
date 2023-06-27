package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目实验名目的附件 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectCategoryAttachmentBaseVO {

    @Schema(description = "实验名目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4209")
    @NotNull(message = "实验名目 id不能为空")
    private Long projectCategoryId;

    @Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "文件名不能为空")
    private String fileName;

    @Schema(description = "文件地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "文件地址不能为空")
    private String fileUrl;

    @Schema(description = "文件类型", example = "1")
    private byte[] type;

}

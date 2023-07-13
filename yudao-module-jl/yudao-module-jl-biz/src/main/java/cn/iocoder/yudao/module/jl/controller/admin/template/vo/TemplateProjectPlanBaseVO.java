package cn.iocoder.yudao.module.jl.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目方案模板 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TemplateProjectPlanBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "模板名称不能为空")
    private String name;

    @Schema(description = "参考文件", example = "张三")
    private String fileName;

    @Schema(description = "参考文件地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "模板：方案富文本", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "方案内容不能为空")
    private String content;

}

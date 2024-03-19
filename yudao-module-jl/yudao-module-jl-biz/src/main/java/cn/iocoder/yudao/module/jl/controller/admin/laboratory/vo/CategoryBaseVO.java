package cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验名目 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CategoryBaseVO {

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "名字不能为空")
    private String name;

    @Schema(description = "技术难度")
    private String difficultyLevel;

    @Schema(description = "重要备注说明")
    private String mark;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "实验室id")
    private Long labId;

    @Schema(description = "历史操作次数", example = "0")
    private Integer actionCount;

    @Schema(description = "原理")
    private String principle;

    @Schema(description = "目的")
    private String purpose;

    @Schema(description = "准备")
    private String preparation;

    @Schema(description = "注意事项")
    private String caution;

    @Schema(description = "标签")
    private String tagIds;

}

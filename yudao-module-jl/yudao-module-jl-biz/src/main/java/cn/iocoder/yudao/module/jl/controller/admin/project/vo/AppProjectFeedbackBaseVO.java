package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.enums.ProjectFeedbackEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 项目反馈 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AppProjectFeedbackBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15989")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;


    @Schema(description = "实验名目 id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "5559")
    private Long projectCategoryId;

    @Schema(description = "售前/售中/售后")
    private String projectStage;

    @Schema(description = "具体的反馈内容")
    private String feedType;

    @Schema(description = "责任人 id", example = "5172")
    private Long userId;

    @Schema(description = "字典：内部反馈/客户反馈", example = "2")
    private String type = ProjectFeedbackEnums.CUSTOMER_TYPE.getStatus();

    @Schema(description = "反馈的内容",requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "这是反馈的内容")
    @NotNull(message = "反馈的内容不能为空")
    private String content;

}

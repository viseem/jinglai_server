package cn.iocoder.yudao.module.jl.controller.admin.workduration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 工时 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class WorkDurationBaseVO {

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "时长(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "时长(分)不能为空")
    private Integer duration;

    @Schema(description = "项目", requiredMode = Schema.RequiredMode.REQUIRED, example = "31128")
    @NotNull(message = "项目不能为空")
    private Long projectId;

    @Schema(description = "实验名目", example = "32707")
    private Long projectCategoryId;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "审批状态", example = "1")
    private String auditStatus;

    @Schema(description = "审批人", example = "1117")
    private Long auditUserId;

    @Schema(description = "审批说明")
    private String auditMark;

}

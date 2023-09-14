package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 项目安排单更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectScheduleUpdatePlanReqVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6289")
    @NotNull(message = "岗位ID不能为空")
    private Long scheduleId;

    private Long projectId;
    private String contentHtml;

    private String newContentHtml;

    private Long projectCategoryId;

    private String projectCategoryContent;

}

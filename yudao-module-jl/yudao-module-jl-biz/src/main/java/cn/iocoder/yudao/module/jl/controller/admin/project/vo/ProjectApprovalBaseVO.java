package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目的状态变更记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectApprovalBaseVO {

    @Schema(description = "申请变更的状态：提前开展、项目开展、暂停、中止、退单、提前出库、出库、售后", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请变更的状态：提前开展、项目开展、暂停、中止、退单、提前出库、出库、售后不能为空")
    private String stage;

    @Schema(description = "申请的备注")
    private String stageMark;


    @Schema(description = "项目的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9195")
    @NotNull(message = "项目的id不能为空")
    private Long projectId;

    @Schema(description = "安排单id")
    private Long scheduleId;


    private String approvalStage = "0";
}

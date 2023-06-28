package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目实验名目的状态变更审批 Order 设置，用于分页使用
 */
@Data
public class ProjectCategoryApprovalPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String stage;

    @Schema(allowableValues = {"desc", "asc"})
    private String stageMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String approvalUserId;

    @Schema(allowableValues = {"desc", "asc"})
    private String approvalMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String approvalStage;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryId;

    @Schema(allowableValues = {"desc", "asc"})
    private String scheduleId;

}

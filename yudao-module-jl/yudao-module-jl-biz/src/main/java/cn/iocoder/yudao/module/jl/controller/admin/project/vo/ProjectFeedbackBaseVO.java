package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.enums.IsOrNotEnums;
import cn.iocoder.yudao.module.jl.enums.IsResolvedEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProjectFeedbackEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目反馈 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectFeedbackBaseVO {

    @Schema(description = "项目 id")
    private Long projectId;


    @Schema(description = "实验名目 id")
    private Long projectCategoryId;

    @Schema(description = "售前/售中/售后")
    private String projectStage;

    @Schema(description = "具体的反馈内容")
    private String feedType;

    @Schema(description = "责任人 id", example = "5172", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull(message = "责任人 id不能为空")
    private Long userId;

    @Schema(description = "客户 id")
    private Long customerId;

    @Schema(description = "字典：内部反馈/客户反馈", example = "2")
    private String type;

    @Schema(description = "状态", example = "1")
    private String status = IsResolvedEnums.NOT.getStatus();

    @Schema(description = "反馈的内容",requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "这是反馈的内容")
    @NotNull(message = "反馈的内容不能为空")
    private String content;

    @Schema(description = "处理结果")
    private String result;

    @Schema(description = "处理时间")
    private LocalDateTime resultTime;



    @Schema(description = "处理人 id")
    private Long resultUserId;

    //时间格式化
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deadline;

    private String level;

    @Schema(description = "附件", example = "2")
    private List<CommonAttachment> attachmentList;

}

package cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目推进记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectPushLogBaseVO {

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5072")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "推进内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "推进内容不能为空")
    private String content;

    @Schema(description = "推进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime recordTime;

    @Schema(description = "阶段")
    private String stage;

    @Schema(description = "进度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "进度不能为空")
    private BigDecimal progress;

    @Schema(description = "预期进度")
    private BigDecimal expectedProgress;

    @Schema(description = "风险")
    private String risk;

    @Schema(description = "备注")
    private String mark;

    private List<CommonAttachment> attachmentList;


}

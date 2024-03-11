package cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目推进记录 Excel 导出 Request VO，参数和 ProjectPushLogPageReqVO 是一致的")
@Data
public class ProjectPushLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目id", example = "5072")
    private Long projectId;

    @Schema(description = "推进内容")
    private String content;

    @Schema(description = "推进时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] recordTime;

    @Schema(description = "阶段")
    private String stage;

    @Schema(description = "进度")
    private BigDecimal progress;

    @Schema(description = "预期进度")
    private BigDecimal expectedProgress;

    @Schema(description = "风险")
    private String risk;

    @Schema(description = "备注")
    private String mark;

}

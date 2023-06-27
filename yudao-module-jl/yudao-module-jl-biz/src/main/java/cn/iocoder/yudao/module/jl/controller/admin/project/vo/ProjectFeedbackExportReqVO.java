package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目反馈 Excel 导出 Request VO，参数和 ProjectFeedbackPageReqVO 是一致的")
@Data
public class ProjectFeedbackExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "15989")
    private Long projectId;

    @Schema(description = "实验名目 id", example = "5559")
    private Long projectCategoryId;

    @Schema(description = "内部人员 id", example = "5172")
    private Long userId;

    @Schema(description = "客户 id", example = "13743")
    private Long customerId;

    @Schema(description = "字典：内部反馈/客户反馈", example = "2")
    private String type;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "反馈的内容")
    private String content;

    @Schema(description = "处理结果")
    private String result;

}

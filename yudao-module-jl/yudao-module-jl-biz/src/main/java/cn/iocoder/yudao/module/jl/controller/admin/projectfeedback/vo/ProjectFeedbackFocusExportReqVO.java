package cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 晶莱项目反馈关注人员 Excel 导出 Request VO，参数和 ProjectFeedbackFocusPageReqVO 是一致的")
@Data
public class ProjectFeedbackFocusExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "用户id", example = "3368")
    private Long userId;

    @Schema(description = "项目反馈id", example = "32185")
    private Long projectFeedbackId;

}
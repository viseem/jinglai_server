package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目实验方案 Excel 导出 Request VO，参数和 ProjectPlanPageReqVO 是一致的")
@Data
public class ProjectPlanExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目id", example = "12094")
    private Integer projectId;

    @Schema(description = "客户id", example = "20351")
    private Integer customerId;

    @Schema(description = "安排单id", example = "15994")
    private Integer scheduleId;

    @Schema(description = "方案text")
    private String planText;

    @Schema(description = "版本")
    private String version;

}
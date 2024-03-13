package cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 拜访记录 Excel 导出 Request VO，参数和 VisitLogPageReqVO 是一致的")
@Data
public class VisitLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "客户id", example = "3189")
    private Long customerId;

    @Schema(description = "销售id", example = "15563")
    private Long salesId;

    @Schema(description = "拜访时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] time;

    @Schema(description = "拜访地址")
    private String address;

    @Schema(description = "拜访目的")
    private String goal;

    @Schema(description = "拜访途径", example = "2")
    private String visitType;

    @Schema(description = "拜访内容")
    private String content;

    @Schema(description = "反馈")
    private String feedback;

    @Schema(description = "评分")
    private Integer score;

    @Schema(description = "备注")
    private String mark;

}

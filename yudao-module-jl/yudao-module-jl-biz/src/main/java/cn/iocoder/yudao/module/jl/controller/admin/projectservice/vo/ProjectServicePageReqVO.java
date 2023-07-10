package cn.iocoder.yudao.module.jl.controller.admin.projectservice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目售后分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectServicePageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "11195")
    private Long projectId;

    @Schema(description = "实验名目 id", example = "31297")
    private Long projectCategoryId;

    @Schema(description = "内部人员 id", example = "6574")
    private Long userId;

    @Schema(description = "客户 id", example = "6806")
    private Long customerId;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "反馈的内容")
    private String content;

    @Schema(description = "处理结果")
    private String result;

    @Schema(description = "处理人", example = "8720")
    private Long resultUserId;

    @Schema(description = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] resultTime;

}

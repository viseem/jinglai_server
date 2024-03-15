package cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工作任务 TODO Excel 导出 Request VO，参数和 WorkTodoPageReqVO 是一致的")
@Data
public class WorkTodoExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "重要程度：重要紧急 不重要不紧急 重要不紧急")
    private String priority;

    @Schema(description = "期望截止日期（排期）")
    private LocalDateTime deadline;

    @Schema(description = "状态：未受理、已处理", example = "2")
    private String status;

    @Schema(description = "类型(系统生成的任务、用户自己创建)", example = "1")
    private String type;

    @Schema(description = "引用的 id", example = "24855")
    private Long refId;

    @Schema(description = "引用的提示语句")
    private String refDesc;

    @Schema(description = "负责人 id", example = "32131")
    private Integer userId;

}

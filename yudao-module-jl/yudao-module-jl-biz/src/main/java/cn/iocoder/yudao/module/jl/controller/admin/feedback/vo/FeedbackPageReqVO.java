package cn.iocoder.yudao.module.jl.controller.admin.feedback.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 晶莱反馈分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "重要程度：重要紧急 不重要不紧急 重要不紧急")
    private String importance;

    @Schema(description = "期望截止日期（排期）")
    private String deadline;

    @Schema(description = "是否排期")
    private Boolean isDeadline;

    @Schema(description = "状态：未受理、处理中、已解决", example = "2")
    private String status;

    @Schema(description = "截图地址", example = "https://www.iocoder.cn")
    private String fileUrl;

}

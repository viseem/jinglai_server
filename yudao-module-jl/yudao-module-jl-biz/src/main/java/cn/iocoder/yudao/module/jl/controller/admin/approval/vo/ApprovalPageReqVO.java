package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 审批分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApprovalPageReqVO extends PageParam {

    private Long creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "内容详情id", example = "6115")
    private Long refId;

    @Schema(description = "类型", example = "1")
    private String type;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "当前审批人")
    private String currentApprovalUser;

    @Schema(description = "当前审批状态")
    private String currentApprovalStage;

}

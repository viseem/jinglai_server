package cn.iocoder.yudao.module.jl.controller.admin.approval.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 审批流程 Excel 导出 Request VO，参数和 ApprovalProgressPageReqVO 是一致的")
@Data
public class ApprovalProgressExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "审批id", example = "31967")
    private Long approvalId;

    @Schema(description = "用户id", example = "14090")
    private Integer toUserId;

    @Schema(description = "审批状态")
    private String approvalStage;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批类型：抄送，审批", example = "2")
    private String type;

}
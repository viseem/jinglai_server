package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目的状态变更记录 Excel 导出 Request VO，参数和 ProjectApprovalPageReqVO 是一致的")
@Data
public class ProjectApprovalExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "申请变更的状态：提前开展、项目开展、暂停、中止、退单、提前出库、出库、售后")
    private String stage;

    @Schema(description = "申请的备注")
    private String stageMark;

    @Schema(description = "审批人id", example = "378")
    private Long approvalUserId;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批状态：等待审批、批准、拒绝")
    private String approvalStage;

    @Schema(description = "项目的id", example = "9195")
    private Long projectId;

    @Schema(description = "安排单id", example = "31892")
    private Long scheduleId;

}

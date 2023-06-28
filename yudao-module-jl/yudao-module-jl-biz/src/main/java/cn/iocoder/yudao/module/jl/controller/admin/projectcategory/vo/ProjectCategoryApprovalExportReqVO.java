package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目实验名目的状态变更审批 Excel 导出 Request VO，参数和 ProjectCategoryApprovalPageReqVO 是一致的")
@Data
public class ProjectCategoryApprovalExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "申请变更的状态：开始、暂停、数据审批")
    private String stage;

    @Schema(description = "申请的备注")
    private String stageMark;

    @Schema(description = "审批人id", example = "26290")
    private Long approvalUserId;

    @Schema(description = "审批备注")
    private String approvalMark;

    @Schema(description = "审批状态：等待审批、已审批")
    private String approvalStage;

    @Schema(description = "项目的实验名目id", example = "24706")
    private Long projectCategoryId;

    @Schema(description = "安排单id", example = "23230")
    private Long scheduleId;

}

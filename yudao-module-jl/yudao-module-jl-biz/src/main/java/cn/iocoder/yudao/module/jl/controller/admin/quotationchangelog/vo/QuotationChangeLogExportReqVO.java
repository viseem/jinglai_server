package cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报价变更日志 Excel 导出 Request VO，参数和 QuotationChangeLogPageReqVO 是一致的")
@Data
public class QuotationChangeLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目id", example = "29980")
    private Long projectId;

    @Schema(description = "报价id", example = "23423")
    private Long qutationId;

    @Schema(description = "客户id", example = "2225")
    private Long customerId;

    @Schema(description = "变更原因")
    private String mark;

    @Schema(description = "变更方案")
    private String planText;

    @Schema(description = "变更明细")
    private String jsonLogs;

}

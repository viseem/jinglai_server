package cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目结算 Excel 导出 Request VO，参数和 ProjectSettlementPageReqVO 是一致的")
@Data
public class ProjectSettlementExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "结算金额")
    private BigDecimal paidAmount;

    @Schema(description = "提醒日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] reminderDate;

    @Schema(description = "应收金额")
    private BigDecimal amount;

    @Schema(description = "备注")
    private String mark;

}

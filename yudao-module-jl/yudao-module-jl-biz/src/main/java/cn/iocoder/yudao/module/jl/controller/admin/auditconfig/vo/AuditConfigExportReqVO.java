package cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 审批配置表  Excel 导出 Request VO，参数和 AuditConfigPageReqVO 是一致的")
@Data
public class AuditConfigExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "路由")
    private String route;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "是否需要审批")
    private Boolean needAudit;

}

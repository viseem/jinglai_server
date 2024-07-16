package cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户变更日志 Excel 导出 Request VO，参数和 CustomerChangeLogPageReqVO 是一致的")
@Data
public class CustomerChangeLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "客户id", example = "31576")
    private Long customerId;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "转给谁", example = "28365")
    private Long toOwnerId;

    @Schema(description = "来自谁", example = "15951")
    private Long fromOwnerId;

}

package cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目报价 Excel 导出 Request VO，参数和 ProjectQuotationPageReqVO 是一致的")
@Data
public class ProjectQuotationExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "版本号")
    private String code;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "方案")
    private String planText;

    @Schema(description = "项目id", example = "19220")
    private Long projectId;

    @Schema(description = "客户id", example = "22455")
    private Long customerId;

}
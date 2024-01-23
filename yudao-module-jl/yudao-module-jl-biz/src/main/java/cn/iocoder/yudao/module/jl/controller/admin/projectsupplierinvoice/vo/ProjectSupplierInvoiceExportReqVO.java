package cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 采购供应商发票 Excel 导出 Request VO，参数和 ProjectSupplierInvoicePageReqVO 是一致的")
@Data
public class ProjectSupplierInvoiceExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "金额", example = "14573")
    private Long price;

    @Schema(description = "凭证", example = "李四")
    private String fileName;

    @Schema(description = "凭证", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "项目id", example = "26620")
    private Long projectId;

    @Schema(description = "供应商id", example = "17470")
    private Long supplierId;

    @Schema(description = "开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] date;

    @Schema(description = "备注")
    private String mark;

}

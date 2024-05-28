package cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 开票申请 Excel 导出 Request VO，参数和 InvoiceApplicationPageReqVO 是一致的")
@Data
public class InvoiceApplicationExportReqVO {



    @Schema(description = "申请单id", example = "31489",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请单id不能为空")
    private Long id;


}

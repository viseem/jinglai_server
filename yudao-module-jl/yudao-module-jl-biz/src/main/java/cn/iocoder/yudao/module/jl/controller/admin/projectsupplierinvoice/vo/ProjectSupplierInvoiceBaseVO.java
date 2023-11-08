package cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 采购供应商发票 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectSupplierInvoiceBaseVO {

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "14573")
    @NotNull(message = "金额不能为空")
    private Long price;

    @Schema(description = "凭证", example = "李四")
    private String fileName;

    @Schema(description = "凭证", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26620")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "供应商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17470")
    @NotNull(message = "供应商id不能为空")
    private Long supplierId;

    @Schema(description = "开票日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime date;

    @Schema(description = "备注")
    private String mark;

}

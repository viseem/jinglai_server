package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目采购单打款分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcurementPaymentPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目id", example = "21105")
    private Long projectId;

    @Schema(description = "采购单id", example = "21105")
    private Long procurementId;

    @Schema(description = "打款时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] paymentDate;

    @Schema(description = "打款金额")
    private String amount;

    @Schema(description = "供货商 id", example = "14390")
    private Long supplierId;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "凭证", example = "王五")
    private String proofName;

    @Schema(description = "凭证地址", example = "https://www.iocoder.cn")
    private String proofUrl;

}

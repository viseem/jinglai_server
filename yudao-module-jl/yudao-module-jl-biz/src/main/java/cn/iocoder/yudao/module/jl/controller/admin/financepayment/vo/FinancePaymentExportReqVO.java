package cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 财务打款 Excel 导出 Request VO，参数和 FinancePaymentPageReqVO 是一致的")
@Data
public class FinancePaymentExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "申请单", example = "19904")
    private Long refId;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "审批人", example = "14780")
    private Long auditUserId;

    @Schema(description = "审批说明")
    private String auditMark;

    @Schema(description = "审批状态", example = "2")
    private String auditStatus;

    @Schema(description = "金额", example = "8057")
    private BigDecimal price;

    @Schema(description = "凭据", example = "https://www.iocoder.cn")
    private String proofUrl;

    @Schema(description = "凭据名称", example = "张三")
    private String proofName;

    @Schema(description = "打款凭证", example = "https://www.iocoder.cn")
    private String paymentUrl;

    @Schema(description = "打款凭证名称", example = "王五")
    private String paymentName;

}

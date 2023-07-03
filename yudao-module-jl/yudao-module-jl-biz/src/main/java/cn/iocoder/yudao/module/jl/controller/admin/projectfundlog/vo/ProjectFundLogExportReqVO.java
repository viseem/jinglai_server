package cn.iocoder.yudao.module.jl.controller.admin.projectfundlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目款项 Excel 导出 Request VO，参数和 ProjectFundLogPageReqVO 是一致的")
@Data
public class ProjectFundLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "收款金额", example = "3943")
    private Long price;

    @Schema(description = "项目 id", example = "10996")
    private Long projectId;

    @Schema(description = "支付凭证上传地址", example = "https://www.iocoder.cn")
    private String receiptUrl;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] paidTime;

    @Schema(description = "支付凭证文件名称", example = "王五")
    private String receiptName;

    @Schema(description = "付款方")
    private String payer;

    @Schema(description = "收款方")
    private String payee;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "项目款项主表id", example = "872")
    private Long projectFundId;

}

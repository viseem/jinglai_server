package cn.iocoder.yudao.module.jl.controller.admin.projectfundlog.vo;

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
 * 项目款项 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectFundLogBaseVO {

    @Schema(description = "收款金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "3943")
    @NotNull(message = "收款金额不能为空")
    private Long price;

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10996")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "支付凭证上传地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "支付凭证上传地址不能为空")
    private String receiptUrl;

    @Schema(description = "支付时间")
    private String paidTime;

    @Schema(description = "支付凭证文件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "支付凭证文件名称不能为空")
    private String receiptName;

    @Schema(description = "付款方", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "付款方不能为空")
    private String payer;

    @Schema(description = "收款方", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收款方不能为空")
    private String payee;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "项目款项主表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "872")
    @NotNull(message = "项目款项主表id不能为空")
    private Long projectFundId;

}

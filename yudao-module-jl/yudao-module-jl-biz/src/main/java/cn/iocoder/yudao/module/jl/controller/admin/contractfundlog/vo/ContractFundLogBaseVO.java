package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
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
 * 合同收款记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContractFundLogBaseVO {

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "31742")
    @NotNull(message = "金额不能为空")
    private Long price;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "凭证地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "凭证地址不能为空")
    private String fileUrl;

    @Schema(description = "凭证", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "凭证不能为空")
    private String fileName;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "支付日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "支付日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime paidTime;

    @Schema(description = "付款方")
    private String payer;

    @Schema(description = "收款方")
    private String payee;

    @Schema(description = "合同id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17163")
    @NotNull(message = "合同id不能为空")
    private Long contractId;

    @Schema(description = "项目id")
    private Long projectId;

    @Schema(description = "客户id")
    private Long customerId;

    private ProjectConstractOnly contract;
}
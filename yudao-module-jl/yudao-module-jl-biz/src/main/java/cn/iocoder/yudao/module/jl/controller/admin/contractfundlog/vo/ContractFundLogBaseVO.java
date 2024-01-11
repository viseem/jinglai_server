package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;

import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.enums.ContractFundStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
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

    @Schema(description = "核验金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "31742")
    @NotNull(message = "核验金额不能为空")
    private BigDecimal price;

    @Schema(description = "金额")
    private BigDecimal receivedPrice;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "凭证地址")
    private String fileUrl;

    @Schema(description = "凭证")
    private String fileName;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "支付日期")
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

    @Schema(description = "销售id")
    private Long salesId;

    @Schema(description = "状态")
    private String status = ContractFundStatusEnums.NOT_AUDIT.getStatus();

    @Schema(description = "状态")
    private Long auditId;

    private ProjectConstractOnly contract;

    private CustomerOnly customer;

    private User auditor;
    private User user;
}

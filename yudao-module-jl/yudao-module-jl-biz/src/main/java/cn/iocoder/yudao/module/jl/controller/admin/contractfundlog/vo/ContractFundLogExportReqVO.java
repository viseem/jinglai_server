package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合同收款记录 Excel 导出 Request VO，参数和 ContractFundLogPageReqVO 是一致的")
@Data
public class ContractFundLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "金额", example = "31742")
    private Long price;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "凭证地址", example = "https://www.iocoder.cn")
    private String fileUrl;

    @Schema(description = "凭证", example = "芋艿")
    private String fileName;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "支付日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] paidTime;

    @Schema(description = "付款方")
    private String payer;

    @Schema(description = "收款方")
    private String payee;

    @Schema(description = "合同id", example = "17163")
    private Long contractId;

    @Schema(description = "项目id", example = "4295")
    private Long projectId;

    @Schema(description = "客户id", example = "11060")
    private Long customerId;

}

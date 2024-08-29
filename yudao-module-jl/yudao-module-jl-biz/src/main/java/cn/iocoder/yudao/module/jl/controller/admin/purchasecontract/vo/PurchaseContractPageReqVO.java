package cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 购销合同分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PurchaseContractPageReqVO extends PageParam {
    @Schema(description = "批准时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] acceptTime;
    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "采购类型", example = "19446")
    private Integer procurementType;

    @Schema(description = "供应商id", example = "19446")
    private Integer supplierId;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "支付状态", example = "1")
    private String priceStatus;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "总价")
    private BigDecimal amount;

}

package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目采购单申请明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProcurementItemBaseVO {
    @Schema(description = "原价")
    private Integer originPrice = 0;

    @Schema(description = "运费")
    private Integer freight = 0;

    @Schema(description = "退款凭证json")
    private String refundReceipts;

    @Schema(description = "退货总个数")
    private Long refundAmount;

    @Schema(description = "退款数量")
    private Long refundQuantity;

    @Schema(description = "预计到达日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime arrivalDate;

    @Schema(description = "采购单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26560")
    @NotNull(message = "采购单id不能为空")
    private Long procurementId;

    @Schema(description = "项目物资 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30207")
    @NotNull(message = "项目物资 id不能为空")
    private Long projectSupplyId;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "名称不能为空")
    private String name;

    private Long scheduleId;

    @Schema(description = "采购规则/单位")
    private String feeStandard;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "单价")
    private String unitFee;

    @Schema(description = "单量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单量不能为空")
    private Integer unitAmount = 0;

    @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采购数量不能为空")
    private Integer quantity = 0;



    @Schema(description = "供货商id")
    private Long supplierId;

    @Schema(description = "原价")
    private Integer buyPrice = 0;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "22107")
    @NotNull(message = "销售价不能为空")
    private Integer salePrice = 0;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "付款周期")
    private String paymentCycle;

    @Schema(description = "有效期", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String validDate;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "货号")
    private String productCode;

    @Schema(description = "目录号")
    private String catalogNumber;

    @Schema(description = "货期")
    private String deliveryDate;

    @Schema(description = "状态:等待采购信息、等待打款、等待采购、等待签收、等待入库", example = "2")
    private String status;

    private Long roomId;
    private Long placeId;
    private Long containerId;
    private String locationName;
    private String temperature;
    private Integer inQuantity = 0;
    private Integer checkInQuantity = 0;
}

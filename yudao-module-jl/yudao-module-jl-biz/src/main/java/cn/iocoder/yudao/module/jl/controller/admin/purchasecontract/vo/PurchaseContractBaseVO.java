package cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import cn.iocoder.yudao.module.jl.enums.ProcurementTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 购销合同 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PurchaseContractBaseVO {

    @Schema(description = "实验室id")
    private Long labId;

    @Schema(description = "采购类型")
    private Integer procurementType= ProcurementTypeEnums.PROJECT.getStatus();

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "供应商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19446")
    @NotNull(message = "供应商id不能为空")
    private Integer supplierId;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private String status="1";

    @Schema(description = "付款状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "付款状态不能为空")
    private String priceStatus="1";

    @Schema(description = "总价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总价不能为空")
    private BigDecimal amount;

    @Schema(description = "流程实例id")
    private String processInstanceId;

    @Schema(description = "审批同意时间", example = "29752")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime acceptTime;

    private List<CommonAttachment> attachmentList;

}

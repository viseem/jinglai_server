package cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * 购销合同 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PurchaseContractBaseVO {

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

    @Schema(description = "总价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总价不能为空")
    private BigDecimal amount;

    private List<CommonAttachment> attachmentList;

}

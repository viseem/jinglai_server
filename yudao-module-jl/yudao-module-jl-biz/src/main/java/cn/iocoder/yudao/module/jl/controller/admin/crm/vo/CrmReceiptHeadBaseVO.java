package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 客户发票抬头 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CrmReceiptHeadBaseVO {

    @Schema(description = "开票类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "开票类型不能为空")
    private String type;

    @Schema(description = "开票抬头", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开票抬头不能为空")
    private String title;

    @Schema(description = "纳税人识别号")
    private String taxerNumber;

    @Schema(description = "开户行", example = "张三")
    private String bankName;

    @Schema(description = "开户账号", example = "27416")
    private String bankAccount;

    @Schema(description = "开票地址")
    private String address;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9271")
    @NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "备注")
    private String mark;

}

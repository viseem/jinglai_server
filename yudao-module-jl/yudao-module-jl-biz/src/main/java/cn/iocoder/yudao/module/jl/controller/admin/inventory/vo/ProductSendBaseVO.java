package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验产品寄送 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductSendBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3772")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "6760")
    private Long projectCategoryId;

    @Schema(description = "寄送单号")
    private String code;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "寄送时间")
    private LocalDateTime sendDate;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "收货人", example = "张三")
    private String receiverName;

    @Schema(description = "收货人电话")
    private String receiverPhone;

}

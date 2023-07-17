package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养鼠牌 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AnimalFeedCardBaseVO {

    @Schema(description = "饲养单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8438")
    @NotNull(message = "饲养单id不能为空")
    private Long feedOrderId;

    @Schema(description = "序号")
    private Integer seq;

    @Schema(description = "品系品种", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "品系品种不能为空")
    private String breed;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "性别", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "性别不能为空")
    private Byte gender;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "项目id", example = "3741")
    private Long projectId;

    @Schema(description = "客户id", example = "6508")
    private Long customerId;

}

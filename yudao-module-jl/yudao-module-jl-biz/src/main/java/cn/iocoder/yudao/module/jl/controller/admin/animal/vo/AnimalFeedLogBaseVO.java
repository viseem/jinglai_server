package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 动物饲养日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AnimalFeedLogBaseVO {

    @Schema(description = "饲养单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "饲养单id不能为空")
    private Long feedOrderId;

    @Schema(description = "变更数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更数量不能为空")
    private Integer changeQuantity;

    @Schema(description = "变更笼数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更笼数不能为空")
    private Integer changeCageQuantity;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "变更附件")
    private String files;

    @Schema(description = "变更位置")
    private String stores;

    @Schema(description = "变更后数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更后数量不能为空")
    private Integer quantity;

    @Schema(description = "变更后笼数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "变更后笼数不能为空")
    private Integer cageQuantity;

}

package cn.iocoder.yudao.module.jl.controller.admin.productsop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 产品sop Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductSopBaseVO {

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "11146")
    @NotNull(message = "产品不能为空")
    private Long productId;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "内容")
    private String content;

}

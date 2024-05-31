package cn.iocoder.yudao.module.jl.controller.admin.productuser.vo;

import cn.iocoder.yudao.module.jl.entity.product.ProductOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 产品库人员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProductUserBaseVO {

    @Schema(description = "产品", requiredMode = Schema.RequiredMode.REQUIRED, example = "2551")
    @NotNull(message = "产品不能为空")
    private Long productId;

    @Schema(description = "用户", requiredMode = Schema.RequiredMode.REQUIRED, example = "3947")
    @NotNull(message = "用户不能为空")
    private Long userId;

    @Schema(description = "备注")
    private String mark;

    private ProductOnly product;
    private User user;

    private String nickname;
}

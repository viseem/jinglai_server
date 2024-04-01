package cn.iocoder.yudao.module.jl.controller.admin.productuser.vo;

import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 产品库人员更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductUserUpdateReqVO extends ProductUserBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26852")
    @NotNull(message = "ID不能为空")
    private Long id;

    private List<ProductUser> list;
}

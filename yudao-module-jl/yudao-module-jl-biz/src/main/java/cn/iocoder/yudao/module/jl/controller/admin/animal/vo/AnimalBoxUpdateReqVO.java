package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 动物笼位更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AnimalBoxUpdateReqVO extends AnimalBoxBaseVO {

    @Schema(description = "``", requiredMode = Schema.RequiredMode.REQUIRED, example = "16609")
    @NotNull(message = "``不能为空")
    private Long id;

}

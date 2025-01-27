package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目实验委外供应商更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategorySupplierUpdateReqVO extends ProjectCategorySupplierBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9628")
    @NotNull(message = "ID不能为空")
    private Long id;

}

package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目实验委外更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryOutsourceUpdateReqVO extends ProjectCategoryOutsourceBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19764")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "原始数据", requiredMode = Schema.RequiredMode.REQUIRED, example = "19764")
    @NotNull(message = "原始数据不能为空")
    private String rawdata;

    @Schema(description = "实验记录")
    private String record;

    private String files;
}

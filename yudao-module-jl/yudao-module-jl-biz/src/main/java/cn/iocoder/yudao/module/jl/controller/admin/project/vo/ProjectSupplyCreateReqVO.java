package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目中的实验名目的物资项创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectSupplyCreateReqVO extends ProjectSupplyBaseVO {

    @Schema(description = "category类型不能为空", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String projectCategoryType;
}

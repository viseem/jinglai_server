package cn.iocoder.yudao.module.jl.controller.admin.projectservice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目售后更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectServiceUpdateReqVO extends ProjectServiceBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12160")
    @NotNull(message = "ID不能为空")
    private Long id;

}

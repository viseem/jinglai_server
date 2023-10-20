package cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 款项计划变更日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectFundChangeLogUpdateReqVO extends ProjectFundChangeLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26400")
    @NotNull(message = "ID不能为空")
    private Long id;

}

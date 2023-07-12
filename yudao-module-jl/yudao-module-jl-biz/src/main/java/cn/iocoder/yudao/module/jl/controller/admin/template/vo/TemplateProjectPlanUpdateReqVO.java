package cn.iocoder.yudao.module.jl.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目方案模板更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemplateProjectPlanUpdateReqVO extends TemplateProjectPlanBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12347")
    @NotNull(message = "岗位ID不能为空")
    private Long id;

}

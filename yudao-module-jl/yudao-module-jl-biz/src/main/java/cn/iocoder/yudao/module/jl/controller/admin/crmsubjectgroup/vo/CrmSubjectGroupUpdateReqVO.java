package cn.iocoder.yudao.module.jl.controller.admin.crmsubjectgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 客户课题组更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmSubjectGroupUpdateReqVO extends CrmSubjectGroupBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2370")
    @NotNull(message = "ID不能为空")
    private Long id;

}

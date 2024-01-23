package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 专题小组更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectGroupUpdateReqVO extends SubjectGroupBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "594")
    @NotNull(message = "ID不能为空")
    private Long id;

}

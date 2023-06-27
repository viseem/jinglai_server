package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目实验名目的附件更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectCategoryAttachmentUpdateReqVO extends ProjectCategoryAttachmentBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20557")
    @NotNull(message = "岗位ID不能为空")
    private Long id;

}

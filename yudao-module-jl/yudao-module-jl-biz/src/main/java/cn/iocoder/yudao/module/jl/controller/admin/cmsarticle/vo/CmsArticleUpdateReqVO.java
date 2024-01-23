package cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 文章更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CmsArticleUpdateReqVO extends CmsArticleBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27217")
    @NotNull(message = "ID不能为空")
    private Long id;

    private String content;

}

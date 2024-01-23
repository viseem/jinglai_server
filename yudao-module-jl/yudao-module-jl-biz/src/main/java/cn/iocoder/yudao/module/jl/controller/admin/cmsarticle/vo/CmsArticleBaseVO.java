package cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 文章 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CmsArticleBaseVO {

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "标题不能为空")
    private String title;

    @Schema(description = "副标题")
    private String subTitle;

    @Schema(description = "浏览次数", example = "8375")
    private Integer lookCount;

    @Schema(description = "浏览次数string", example = "8375")
    private String lookCountStr;

    @Schema(description = "排序")
    private Integer sort=0;

    @Schema(description = "封面", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "封面不能为空")
    private String coverUrl;

    private String link;
    private String type;

}

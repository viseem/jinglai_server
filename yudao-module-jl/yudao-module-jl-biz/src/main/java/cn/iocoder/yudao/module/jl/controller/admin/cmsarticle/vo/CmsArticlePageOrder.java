package cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 文章 Order 设置，用于分页使用
 */
@Data
public class CmsArticlePageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String title;

    @Schema(allowableValues = {"desc", "asc"})
    private String subTitle;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

    @Schema(allowableValues = {"desc", "asc"})
    private String lookCount;

    @Schema(allowableValues = {"desc", "asc"})
    private String sort;

}

package cn.iocoder.yudao.module.jl.controller.admin.cmsarticle.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 文章 Excel 导出 Request VO，参数和 CmsArticlePageReqVO 是一致的")
@Data
public class CmsArticleExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "副标题")
    private String subTitle;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "浏览次数", example = "8375")
    private Integer lookCount;

    @Schema(description = "排序")
    private Integer sort;

}

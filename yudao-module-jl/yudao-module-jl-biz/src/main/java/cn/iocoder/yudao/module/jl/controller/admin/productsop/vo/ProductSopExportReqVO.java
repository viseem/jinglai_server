package cn.iocoder.yudao.module.jl.controller.admin.productsop.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品sop Excel 导出 Request VO，参数和 ProductSopPageReqVO 是一致的")
@Data
public class ProductSopExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "产品", example = "11146")
    private Long productId;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "内容")
    private String content;

}
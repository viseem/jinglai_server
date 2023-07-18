package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动物饲养日志 Excel 导出 Request VO，参数和 AnimalFeedLogPageReqVO 是一致的")
@Data
public class AnimalFeedLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "饲养单id", example = "3235")
    private Integer feedOrderId;

    @Schema(description = "变更数量")
    private Integer changeQuantity;

    @Schema(description = "变更笼数")
    private Integer changeCageQuantity;

    @Schema(description = "类型", example = "2")
    private String type;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "变更附件")
    private String files;

    @Schema(description = "变更位置")
    private String stores;

    @Schema(description = "变更后数量")
    private Integer quantity;

    @Schema(description = "变更后笼数")
    private Integer cageQuantity;

}

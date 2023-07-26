package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动物饲养入库 Excel 导出 Request VO，参数和 AnimalFeedStoreInPageReqVO 是一致的")
@Data
public class AnimalFeedStoreInExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "饲养单id", example = "2577")
    private Long feedOrderId;

    @Schema(description = "房间id", example = "22761")
    private Long roomId;

    @Schema(description = "架子ids")
    private String shelfIds;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "位置")
    private String location;

}

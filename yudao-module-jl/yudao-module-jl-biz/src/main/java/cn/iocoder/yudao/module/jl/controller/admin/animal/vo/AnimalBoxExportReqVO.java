package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 动物笼位 Excel 导出 Request VO，参数和 AnimalBoxPageReqVO 是一致的")
@Data
public class AnimalBoxExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "容量")
    private Integer capacity;

    @Schema(description = "现有")
    private Integer quantity;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "架子id", example = "17453")
    private Long shelfId;

    @Schema(description = "房间id", example = "9431")
    private Long roomId;

    @Schema(description = "行索引")
    private Integer rowIndex;

    @Schema(description = "列索引")
    private Integer colIndex;

    @Schema(description = "饲养单", example = "16046")
    private Long feedOrderId;

}

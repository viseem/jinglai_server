package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 库管存储容器 Excel 导出 Request VO，参数和 InventoryContainerPageReqVO 是一致的")
@Data
public class InventoryContainerExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "库房id：可以用字典", example = "26015")
    private Integer roomId;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "所在位置")
    private String place;

    @Schema(description = "负责人", example = "1556")
    private String guardianUserId;

    @Schema(description = "备注描述")
    private String mark;

}

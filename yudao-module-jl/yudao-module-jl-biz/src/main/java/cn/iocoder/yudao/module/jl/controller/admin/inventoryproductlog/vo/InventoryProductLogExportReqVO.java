package cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品变更日志 Excel 导出 Request VO，参数和 InventoryProductLogPageReqVO 是一致的")
@Data
public class InventoryProductLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "物资id", example = "14816")
    private Integer projectSupplyId;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "实验人员id", example = "11711")
    private Long experId;

}

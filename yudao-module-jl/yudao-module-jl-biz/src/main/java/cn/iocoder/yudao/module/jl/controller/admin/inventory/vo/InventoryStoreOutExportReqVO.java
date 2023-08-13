package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 出库记录 Excel 导出 Request VO，参数和 InventoryStoreOutPageReqVO 是一致的")
@Data
public class InventoryStoreOutExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "2664")
    private Long projectId;

    @Schema(description = "实验物资名称 id", example = "6996")
    private Long projectSupplyId;

    @Schema(description = "出库数量")
    private Integer outQuantity;

    @Schema(description = "类型，产品入库，客户寄来", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "产品入库，客户寄来的 id", example = "24779")
    private Long refId;

    @Schema(description = "产品入库，客户寄来的子元素 id", example = "8108")
    private Long refItemId;

    @Schema(description = "保存温度")
    private String temperature;

    @Schema(description = "有效截止期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] validDate;

    @Schema(description = "申请人")
    private Long applyUser;

}

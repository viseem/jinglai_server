package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 出库申请明细 Excel 导出 Request VO，参数和 SupplyOutItemPageReqVO 是一致的")
@Data
public class SupplyOutItemExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "出库申请表 id", example = "756")
    private Long supplyOutId;

    @Schema(description = "物资 id", example = "30488")
    private Long projectSupplyId;

    @Schema(description = "物资库的物资 id", example = "8449")
    private Long supplyId;

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "规则/单位")
    private String feeStandard;

    @Schema(description = "单价")
    private String unitFee;

    @Schema(description = "单量")
    private Integer unitAmount;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注")
    private String mark;

}

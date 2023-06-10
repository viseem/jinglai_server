package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 实验产品入库明细 Excel 导出 Request VO，参数和 ProductInItemPageReqVO 是一致的")
@Data
public class ProductInItemExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "实验产品入库表 id", example = "17715")
    private Long productInId;

    @Schema(description = "产自实验物资的 id", example = "15067")
    private Long sourceSupplyId;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "规则/单位")
    private String feeStandard;

    @Schema(description = "单价")
    private String unitFee;

    @Schema(description = "单量")
    private Integer unitAmount;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "房间 id", example = "20700")
    private Long roomId;

    @Schema(description = "储存器材 id", example = "30446")
    private Long containerId;

    @Schema(description = "区域位置 id", example = "11044")
    private Long zoomId;

    @Schema(description = "有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] validDate;

    @Schema(description = "存储温度")
    private String temperature;

}

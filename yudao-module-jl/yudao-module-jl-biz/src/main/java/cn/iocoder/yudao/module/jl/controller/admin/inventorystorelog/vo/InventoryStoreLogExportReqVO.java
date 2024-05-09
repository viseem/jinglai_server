package cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 物品出入库日志 Excel 导出 Request VO，参数和 InventoryStoreLogPageReqVO 是一致的")
@Data
public class InventoryStoreLogExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "物品来源")
    private String source;

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "目录号")
    private String catalogNumber;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "变更数量")
    private Integer changeNum;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "房间id", example = "14616")
    private Long roomId;

    @Schema(description = "容器id", example = "3090")
    private Long containerId;

    @Schema(description = "位置id", example = "4816")
    private Long placeId;

    @Schema(description = "位置详情")
    private String location;

    @Schema(description = "明细的id", example = "16824")
    private Long sourceItemId;

    @Schema(description = "项目物资的id", example = "18751")
    private Long projectSupplyId;

    @Schema(description = "项目id", example = "20536")
    private Long projectId;

    @Schema(description = "购销合同id", example = "28993")
    private Long purchaseContractId;

    @Schema(description = "采购单id", example = "10104")
    private Long procurementId;

}

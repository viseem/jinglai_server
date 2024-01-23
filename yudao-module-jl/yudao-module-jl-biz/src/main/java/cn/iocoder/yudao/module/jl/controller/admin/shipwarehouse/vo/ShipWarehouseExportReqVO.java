package cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 收货仓库 Excel 导出 Request VO，参数和 ShipWarehousePageReqVO 是一致的")
@Data
public class ShipWarehouseExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "负责人", example = "3646")
    private Long managerId;

    @Schema(description = "详细地址")
    private String address;

}

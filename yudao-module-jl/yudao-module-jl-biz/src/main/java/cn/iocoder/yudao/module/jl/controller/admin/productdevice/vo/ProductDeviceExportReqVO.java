package cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 产品库设备 Excel 导出 Request VO，参数和 ProductDevicePageReqVO 是一致的")
@Data
public class ProductDeviceExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "产品", example = "26758")
    private Long productId;

    @Schema(description = "设备", example = "29014")
    private Long deviceId;

    @Schema(description = "备注")
    private String mark;

}

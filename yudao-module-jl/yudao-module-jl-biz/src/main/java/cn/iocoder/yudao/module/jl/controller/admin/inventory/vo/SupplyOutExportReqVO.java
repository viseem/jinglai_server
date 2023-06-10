package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 出库申请 Excel 导出 Request VO，参数和 SupplyOutPageReqVO 是一致的")
@Data
public class SupplyOutExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "1547")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "11727")
    private Long projectCategoryId;

    @Schema(description = "说明")
    private String mark;

    @Schema(description = "状态", example = "1")
    private String status;

}

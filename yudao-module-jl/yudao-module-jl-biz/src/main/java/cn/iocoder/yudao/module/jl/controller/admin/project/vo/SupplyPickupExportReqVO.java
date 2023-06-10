package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 取货单申请 Excel 导出 Request VO，参数和 SupplyPickupPageReqVO 是一致的")
@Data
public class SupplyPickupExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "11363")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "12631")
    private Long projectCategoryId;

    @Schema(description = "取货单号")
    private String code;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "取货时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] sendDate;

    @Schema(description = "取货人", example = "24449")
    private Long userId;

    @Schema(description = "取货地址")
    private String address;

    @Schema(description = "联系人姓名", example = "赵六")
    private String contactName;

    @Schema(description = "联系人电话")
    private String contactPhone;

}

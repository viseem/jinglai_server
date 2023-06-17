package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 物资寄来单申请 Excel 导出 Request VO，参数和 SupplySendInPageReqVO 是一致的")
@Data
public class SupplySendInExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "28953")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "32059")
    private Long projectCategoryId;

    @Schema(description = "寄来单号")
    private String code;

    @Schema(description = "寄来物流单号")
    private String shipmentNumber;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "寄来时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendDate;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "收货人", example = "芋艿")
    private String receiverName;

    @Schema(description = "收货人电话")
    private String receiverPhone;

}

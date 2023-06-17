package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 实验产品寄送 Excel 导出 Request VO，参数和 ProductSendPageReqVO 是一致的")
@Data
public class ProductSendExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "3772")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "6760")
    private Long projectCategoryId;

    @Schema(description = "寄送单号")
    private String code;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "寄送时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendDate;

    @Schema(description = "收货地址")
    private String address;

    @Schema(description = "收货人", example = "张三")
    private String receiverName;

    @Schema(description = "收货人电话")
    private String receiverPhone;

}

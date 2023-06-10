package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 物资寄来单申请明细 Excel 导出 Request VO，参数和 SupplySendInItemPageReqVO 是一致的")
@Data
public class SupplySendInItemExportReqVO {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "项目 id", example = "11130")
    private Long projectId;

    @Schema(description = "实验名目库的名目 id", example = "27443")
    private Long projectCategoryId;

    @Schema(description = "物资寄来申请表id", example = "22422")
    private Long supplySendInId;

    @Schema(description = "项目物资 id", example = "20737")
    private Long projectSupplyId;

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

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] validDate;

    @Schema(description = "存储温度")
    private String temperature;

    @Schema(description = "状态", example = "2")
    private String status;

}

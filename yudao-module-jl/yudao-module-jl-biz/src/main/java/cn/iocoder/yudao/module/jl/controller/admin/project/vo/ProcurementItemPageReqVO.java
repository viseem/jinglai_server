package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 项目采购单申请明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcurementItemPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "采购单id", example = "26560")
    private Long procurementId;

    @Schema(description = "项目物资 id", example = "30207")
    private Long projectSupplyId;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "采购规则/单位")
    private String feeStandard;

    @Schema(description = "单价")
    private String unitFee;

    @Schema(description = "单量")
    private Integer unitAmount;

    @Schema(description = "采购数量")
    private Integer quantity;

    @Schema(description = "供货商id", example = "6961")
    private Long supplierId;

    @Schema(description = "原价", example = "31202")
    private Integer buyPrice;

    @Schema(description = "销售价", example = "22107")
    private Integer salePrice;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] validDate;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "目录号")
    private String catalogNumber;

    @Schema(description = "货期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] deliveryDate;

    @Schema(description = "状态:等待采购信息、等待打款、等待采购、等待签收、等待入库", example = "2")
    private String status;

}

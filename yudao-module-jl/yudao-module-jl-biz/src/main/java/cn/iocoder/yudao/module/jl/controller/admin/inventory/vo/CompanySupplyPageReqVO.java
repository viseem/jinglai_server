package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公司实验物资库存分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompanySupplyPageReqVO extends PageParam {
    @Schema(description = "来源", example = "1：录入 2：转入")
    private String source;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "品牌", example = "芋艿")
    private String brand;

    @Schema(description = "货号", example = "芋艿")
    private String productCode;

    @Schema(description = "规则/单位")
    private String feeStandard;

    @Schema(description = "单量")
    private Integer unitAmount;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "物资 id", example = "5243")
    private Long supplyId;

    @Schema(description = "存储位置")
    private String location;

    @Schema(description = "项目物资id", example = "5300")
    private Long projectSupplyId;

    @Schema(description = "所属客户", example = "23534")
    private Long customerId;

    @Schema(description = "所属项目", example = "12689")
    private Long projectId;

    @Schema(description = "所属类型：公司、客户", example = "1")
    private String ownerType;

    @Schema(description = "单价")
    private String unitFee;

    @Schema(description = "有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] validDate;

    @Schema(description = "物资快照名称", example = "王五")
    private String fileName;

    @Schema(description = "物资快照地址", example = "https://www.iocoder.cn")
    private String fileUrl;

}

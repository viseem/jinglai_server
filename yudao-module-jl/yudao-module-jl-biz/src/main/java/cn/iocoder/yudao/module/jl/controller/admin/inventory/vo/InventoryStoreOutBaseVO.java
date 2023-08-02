package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 出库记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InventoryStoreOutBaseVO {

    @Schema(description = "项目 id", example = "2664")
    private Long projectId;

    @Schema(description = "实验物资名称 id", example = "6996")
    private Long projectSupplyId;

    @Schema(description = "出库数量")
    private Integer outQuantity;

    @Schema(description = "类型，产品入库，客户寄来", example = "1")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "2")
    private String status;

    @Schema(description = "产品入库，客户寄来的 id", example = "24779")
    private Long refId;

    @Schema(description = "产品入库，客户寄来的子元素 id", example = "8108")
    private Long refItemId;

    @Schema(description = "保存温度")
    private String temperature;

    @Schema(description = "有效截止期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime validDate;

    @Schema(description = "申请人")
    private Long applyUser;

}

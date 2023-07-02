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
 * 入库记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InventoryStoreInBaseVO {

    @Schema(description = "项目 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "14916")
    @NotNull(message = "项目 id不能为空")
    private Long projectId;

    @Schema(description = "实验物资名称 id", example = "18112")
    private Long projectSupplyId;

    @Schema(description = "入库数量")
    private Integer inQuantity;

    @Schema(description = "类型，采购，寄送，自取", example = "2")
    private String type;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "采购，寄送，自取的 id", example = "28615")
    private Long refId;

    @Schema(description = "采购，寄送，自取的子元素 id", example = "231")
    private Long refItemId;

    @Schema(description = "库房 id", example = "3870")
    private Long roomId;

    @Schema(description = "柜子id", example = "13890")
    private Long containerId;

    @Schema(description = "位置id", example = "28042")
    private Long placeId;

    @Schema(description = "保存温度")
    private String temperature;

    @Schema(description = "有效截止期")
    private String validDate;

}

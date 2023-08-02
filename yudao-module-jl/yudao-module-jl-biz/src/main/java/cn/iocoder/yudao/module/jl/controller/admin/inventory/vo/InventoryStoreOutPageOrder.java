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
 * 出库记录 Order 设置，用于分页使用
 */
@Data
public class InventoryStoreOutPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectSupplyId;

    @Schema(allowableValues = {"desc", "asc"})
    private String outQuantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String refId;

    @Schema(allowableValues = {"desc", "asc"})
    private String refItemId;

    @Schema(allowableValues = {"desc", "asc"})
    private String temperature;

    @Schema(allowableValues = {"desc", "asc"})
    private String validDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String applyUser;

}

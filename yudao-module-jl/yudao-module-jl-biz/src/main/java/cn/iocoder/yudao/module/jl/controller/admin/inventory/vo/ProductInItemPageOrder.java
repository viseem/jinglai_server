package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 实验产品入库明细 Order 设置，用于分页使用
 */
@Data
public class ProductInItemPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String productInId;

    @Schema(allowableValues = {"desc", "asc"})
    private String sourceSupplyId;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String feeStandard;

    @Schema(allowableValues = {"desc", "asc"})
    private String unitFee;

    @Schema(allowableValues = {"desc", "asc"})
    private String unitAmount;

    @Schema(allowableValues = {"desc", "asc"})
    private String quantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String roomId;

    @Schema(allowableValues = {"desc", "asc"})
    private String containerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String zoomId;

    @Schema(allowableValues = {"desc", "asc"})
    private String validDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String temperature;

}

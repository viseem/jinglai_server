package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 公司实验物资库存 Order 设置，用于分页使用
 */
@Data
public class CompanySupplyPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplyId;

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
    private String validDate;

}
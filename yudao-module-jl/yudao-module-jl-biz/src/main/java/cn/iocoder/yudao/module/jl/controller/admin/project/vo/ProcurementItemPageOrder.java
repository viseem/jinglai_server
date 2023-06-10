package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目采购单申请明细 Order 设置，用于分页使用
 */
@Data
public class ProcurementItemPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String procurementId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectSupplyId;

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
    private String supplierId;

    @Schema(allowableValues = {"desc", "asc"})
    private String buyPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String salePrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String validDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String brand;

    @Schema(allowableValues = {"desc", "asc"})
    private String catalogNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String deliveryDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

}

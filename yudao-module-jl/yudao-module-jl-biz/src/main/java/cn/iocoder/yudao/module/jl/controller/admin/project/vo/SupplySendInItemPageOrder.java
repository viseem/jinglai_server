package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 物资寄来单申请明细 Order 设置，用于分页使用
 */
@Data
public class SupplySendInItemPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryId;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplySendInId;

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
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String validDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String temperature;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

}

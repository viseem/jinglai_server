package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 动物饲养申请单 Order 设置，用于分页使用
 */
@Data
public class AnimalFeedOrderPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String code;

    @Schema(allowableValues = {"desc", "asc"})
    private String breed;

    @Schema(allowableValues = {"desc", "asc"})
    private String age;

    @Schema(allowableValues = {"desc", "asc"})
    private String quantity;

    @Schema(allowableValues = {"desc", "asc"})
    private String femaleCount;

    @Schema(allowableValues = {"desc", "asc"})
    private String maleCount;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplierId;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplierName;

    @Schema(allowableValues = {"desc", "asc"})
    private String certificateNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String licenseNumber;

    @Schema(allowableValues = {"desc", "asc"})
    private String startDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String endDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String hasDanger;

    @Schema(allowableValues = {"desc", "asc"})
    private String feedType;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String stage;

    @Schema(allowableValues = {"desc", "asc"})
    private String reply;

}

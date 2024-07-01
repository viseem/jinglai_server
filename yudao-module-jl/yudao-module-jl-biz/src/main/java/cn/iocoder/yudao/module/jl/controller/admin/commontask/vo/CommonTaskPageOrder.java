package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

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
 * 通用任务 Order 设置，用于分页使用
 */
@Data
public class CommonTaskPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String name;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String startDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String endDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String userId;

    @Schema(allowableValues = {"desc", "asc"})
    private String focusIds;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String chargeitemId;

    @Schema(allowableValues = {"desc", "asc"})
    private String productId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryId;

    private Long quotationId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectName;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerName;

    @Schema(allowableValues = {"desc", "asc"})
    private String chargeitemName;

    @Schema(allowableValues = {"desc", "asc"})
    private String productName;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryName;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String level;

    @Schema(allowableValues = {"desc", "asc"})
    private String assignUserId;

    @Schema(allowableValues = {"desc", "asc"})
    private String assignUserName;

    @Schema(allowableValues = {"desc", "asc"})
    private String labIds;

    @Schema(allowableValues = {"desc", "asc"})
    private String sort;

}

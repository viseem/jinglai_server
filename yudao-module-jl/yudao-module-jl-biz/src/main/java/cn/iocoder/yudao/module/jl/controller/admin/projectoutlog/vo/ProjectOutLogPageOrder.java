package cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * example 空 Order 设置，用于分页使用
 */
@Data
public class ProjectOutLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String dataSignJson;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerComment;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerScoreJson;

    @Schema(allowableValues = {"desc", "asc"})
    private String quotationPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String quotationMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String receivedPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String receivedMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String contractPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String contractMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplyPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplyMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String outsourcePrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String outsourceMark;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerSignImgUrl;

    @Schema(allowableValues = {"desc", "asc"})
    private String custoamerSignTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String stepsJsonLog;

    @Schema(allowableValues = {"desc", "asc"})
    private String resultPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String resultMark;

}

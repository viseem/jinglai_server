package cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo;

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
 * 拜访记录 Order 设置，用于分页使用
 */
@Data
public class VisitLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String salesId;

    @Schema(allowableValues = {"desc", "asc"})
    private String time;

    @Schema(allowableValues = {"desc", "asc"})
    private String address;

    @Schema(allowableValues = {"desc", "asc"})
    private String goal;

    @Schema(allowableValues = {"desc", "asc"})
    private String visitType;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

    @Schema(allowableValues = {"desc", "asc"})
    private String feedback;

    @Schema(allowableValues = {"desc", "asc"})
    private String score;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}

package cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目结算 Order 设置，用于分页使用
 */
@Data
public class ProjectSettlementPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String paidAmount;

    @Schema(allowableValues = {"desc", "asc"})
    private String reminderDate;

    @Schema(allowableValues = {"desc", "asc"})
    private String amount;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}

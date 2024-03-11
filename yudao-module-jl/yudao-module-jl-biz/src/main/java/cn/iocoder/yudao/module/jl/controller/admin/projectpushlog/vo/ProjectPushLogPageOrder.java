package cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 项目推进记录 Order 设置，用于分页使用
 */
@Data
public class ProjectPushLogPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

    @Schema(allowableValues = {"desc", "asc"})
    private String recordTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String stage;

    @Schema(allowableValues = {"desc", "asc"})
    private String progress;

    @Schema(allowableValues = {"desc", "asc"})
    private String expectedProgress;

    @Schema(allowableValues = {"desc", "asc"})
    private String risk;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}

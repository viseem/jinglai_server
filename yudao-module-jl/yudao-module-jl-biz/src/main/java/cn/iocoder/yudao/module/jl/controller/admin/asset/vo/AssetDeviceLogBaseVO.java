package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

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
 * 公司资产（设备）预约 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AssetDeviceLogBaseVO {

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1696")
    @NotNull(message = "设备id不能为空")
    private Long deviceId;

    @Schema(description = "预约说明")
    private String mark;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startDate;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endDate;

    @Schema(description = "项目id", example = "13618")
    private Long projectId;

    @Schema(description = "用途分类", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private String useType;

    @Schema(description = "项目设备id", example = "29474")
    private Long projectDeviceId;

    private Long customerId;
}

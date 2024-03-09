package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公司资产（设备）预约分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssetDeviceLogPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "设备id", example = "1696")
    private Long deviceId;

    @Schema(description = "预约说明")
    private String mark;

    @Schema(description = "开始时间")
    private LocalDateTime startData;

    @Schema(description = "结束时间")
    private LocalDateTime endData;

    @Schema(description = "预约人id", example = "13618")
    private Long creator;

    @Schema(description = "项目id", example = "13618")
    private Long projectId;

    @Schema(description = "客户id", example = "13618")
    private Long customerId;

    @Schema(description = "用途分类：项目", example = "2")
    private String useType;

    @Schema(description = "项目设备id", example = "29474")
    private Long projectDeviceId;

}

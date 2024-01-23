package cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 晶莱到访预约创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VisitAppointmentCreateReqVO extends VisitAppointmentBaseVO {

    private String deviceMark;
    @Schema(description = "设备预约开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deviceStartTime;

    @Schema(description = "设备预约结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deviceEndTime;

}

package cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 晶莱到访预约分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VisitAppointmentPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "姓名", example = "赵六")
    private String name;

    @Schema(description = "陪同人员id", example = "24986")
    private Long userId;

    @Schema(description = "设备id", example = "9883")
    private Long deviceId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "到访时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] visitTime;

    @Schema(description = "客户id", example = "11048")
    private Long customerId;

}

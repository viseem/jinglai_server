package cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo;

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
 * 晶莱到访预约 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class VisitAppointmentBaseVO {

    @Schema(description = "姓名", example = "赵六")
    private String name;

    @Schema(description = "陪同人员id", example = "24986")
    private Long userId;

    @Schema(description = "设备id", example = "9883")
    private Long deviceId;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "到访时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "到访时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime visitTime;

    @Schema(description = "客户id")
    private Long customerId;

}

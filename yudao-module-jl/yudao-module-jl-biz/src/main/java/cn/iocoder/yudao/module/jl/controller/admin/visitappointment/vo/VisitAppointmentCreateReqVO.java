package cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 晶莱到访预约创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VisitAppointmentCreateReqVO extends VisitAppointmentBaseVO {

}

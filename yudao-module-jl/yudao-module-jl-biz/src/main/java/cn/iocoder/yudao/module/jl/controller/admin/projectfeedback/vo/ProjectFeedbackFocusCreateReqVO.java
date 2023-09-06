package cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 晶莱项目反馈关注人员创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProjectFeedbackFocusCreateReqVO extends ProjectFeedbackFocusBaseVO {

}

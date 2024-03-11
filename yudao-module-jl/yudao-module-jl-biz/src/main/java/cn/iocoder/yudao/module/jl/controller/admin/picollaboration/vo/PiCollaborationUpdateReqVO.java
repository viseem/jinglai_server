package cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - PI组协作更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PiCollaborationUpdateReqVO extends PiCollaborationBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24527")
    @NotNull(message = "ID不能为空")
    private Long id;

}

package cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - pi组协作明细更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PiCollaborationItemUpdateReqVO extends PiCollaborationItemBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13963")
    @NotNull(message = "ID不能为空")
    private Long id;

}

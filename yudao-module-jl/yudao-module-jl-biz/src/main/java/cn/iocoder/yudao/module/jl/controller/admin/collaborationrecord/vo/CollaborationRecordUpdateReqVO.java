package cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 通用协作记录更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CollaborationRecordUpdateReqVO extends CollaborationRecordBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9338")
    @NotNull(message = "ID不能为空")
    private Long id;

}

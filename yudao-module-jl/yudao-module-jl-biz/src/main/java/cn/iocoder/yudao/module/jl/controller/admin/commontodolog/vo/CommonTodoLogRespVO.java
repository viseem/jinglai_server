package cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo;

import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 通用TODO记录 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommonTodoLogRespVO extends CommonTodoLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11333")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private User updateUser;

}

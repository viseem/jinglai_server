package cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 工作任务 TODO创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoCreateReqVO extends WorkTodoBaseVO {

    @Schema(description = "负责人 id", example = "32131")
    private Integer userId;

}

package cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 工作任务 TODO更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkTodoUpdateReqVO extends WorkTodoBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8372")
    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "状态：未受理、已处理", example = "2")
    private String status;

    @Schema(description = "类型(系统生成的任务、用户自己创建)", example = "1")
    private String type;

    @Schema(description = "引用的 id", example = "24855")
    private Long refId;

    @Schema(description = "引用的提示语句")
    private String refDesc;

    @Schema(description = "负责人 id", example = "32131")
    private Integer userId;

}

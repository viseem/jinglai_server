package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo;

import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 专题小组 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SubjectGroupBaseVO {

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "名称不能为空")
    private String name;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "备注不能为空")
    private String mark;

    @Schema(description = "专题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "专题不能为空")
    private String area;

    @Schema(description = "领域", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "领域不能为空")
    private String subject;

    @Schema(description = "组长", requiredMode = Schema.RequiredMode.REQUIRED, example = "27395")
    @NotNull(message = "组长不能为空")
    private Long leaderId;

    @Schema(description = "商户组长")
    private Long businessLeaderId;

    @Schema(description = "编号")
    private String code;

    private User leaderUser;
    private User businessLeaderUser;

}

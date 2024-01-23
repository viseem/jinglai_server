package cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 专题小组成员更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubjectGroupMemberUpdateReqVO extends SubjectGroupMemberBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25802")
    @NotNull(message = "ID不能为空")
    private Long id;

}

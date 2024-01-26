package cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo;

import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 专题小组成员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SubjectGroupMemberBaseVO {

    @Schema(description = "分组id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4881")
    @NotNull(message = "分组id不能为空")
    private Long groupId;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29187")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "备注")
    private String mark;

    private User user;
    private SubjectGroup group;

    private BigDecimal kpiOrderFund;
    private BigDecimal kpiReturnFund;

    private String nickname;
    private String role;
    private String post;

}

package cn.iocoder.yudao.module.jl.controller.admin.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 部门 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DeptBaseVO {

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "部门名称不能为空")
    private String name;

    @Schema(description = "父部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7034")
    @NotNull(message = "父部门id不能为空")
    private Long parentId;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "负责人", example = "23928")
    private Long leaderUserId;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "部门状态（0正常 1停用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "部门状态（0正常 1停用）不能为空")
    private Byte status;

}
